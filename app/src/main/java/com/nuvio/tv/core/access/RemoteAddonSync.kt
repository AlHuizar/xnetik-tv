package us.xnetik.tv.core.access

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import us.xnetik.tv.domain.repository.AddonRepository
import java.util.concurrent.TimeUnit

private const val TAG = "RemoteAddonSync"
private const val ADDONS_URL = "https://telly.xnetik.us/api/addons.json"

suspend fun syncRemoteAddons(addonRepository: AddonRepository) = withContext(Dispatchers.IO) {
    try {
        val client = OkHttpClient.Builder()
            .connectTimeout(6, TimeUnit.SECONDS)
            .readTimeout(6, TimeUnit.SECONDS)
            .build()
        val response = client.newCall(
            Request.Builder().url(ADDONS_URL).get().build()
        ).execute()
        val body = response.body?.string() ?: return@withContext
        val remoteUrls = JSONObject(body)
            .getJSONArray("addons")
            .let { arr -> (0 until arr.length()).map { arr.getString(it) } }

        val installed = addonRepository.getInstalledAddons().first().map { it.baseUrl }

        remoteUrls.forEach { url ->
            if (installed.none { it.equals(url.trimEnd('/'), ignoreCase = true) }) {
                Log.d(TAG, "Auto-installing: $url")
                addonRepository.addAddon(url)
            }
        }

        // Remove addons that were pushed remotely but are no longer in the list
        installed.forEach { installedUrl ->
            val stillWanted = remoteUrls.any { it.trimEnd('/').equals(installedUrl, ignoreCase = true) }
            if (!stillWanted && installedUrl.contains("xnetik") || installedUrl.contains("telly")) {
                // Only auto-remove addons that came from your server — never touch user-added ones
                Log.d(TAG, "Auto-removing: $installedUrl")
                addonRepository.removeAddon(installedUrl)
            }
        }
    } catch (e: Exception) {
        Log.w(TAG, "Remote addon sync failed (non-fatal): ${e.message}")
    }
}
