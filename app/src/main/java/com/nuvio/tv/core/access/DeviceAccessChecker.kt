package us.xnetik.tv.core.access

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.TimeUnit

object DeviceAccessChecker {

    private const val PREFS = "xnetik_device"
    private const val KEY_ID = "device_id"
    private const val API_URL = "https://telly.xnetik.us/api/device/check"

    fun getDeviceId(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getString(KEY_ID, null) ?: run {
            val raw = UUID.randomUUID().toString().replace("-", "").uppercase()
            // Format as XNET-XXXX-XXXX using first 8 hex chars
            val id = "XNET-${raw.substring(0, 4)}-${raw.substring(4, 8)}"
            prefs.edit().putString(KEY_ID, id).apply()
            id
        }
    }

    suspend fun check(context: Context): DeviceStatus = withContext(Dispatchers.IO) {
        val deviceId = getDeviceId(context)
        try {
            val client = OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder()
                .url("$API_URL?id=${deviceId}")
                .get()
                .build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@withContext DeviceStatus.ACTIVE
            val status = JSONObject(body).optString("status", "active")
            when (status.lowercase()) {
                "locked", "suspended", "inactive" -> DeviceStatus.LOCKED
                else -> DeviceStatus.ACTIVE
            }
        } catch (e: Exception) {
            // If server is unreachable, allow access — don't lock out on network error
            DeviceStatus.ACTIVE
        }
    }
}

enum class DeviceStatus { ACTIVE, LOCKED }
