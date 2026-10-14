package us.xnetik.tv.core.di

import us.xnetik.tv.BuildConfig
import us.xnetik.tv.core.network.NoDelaySocketFactory
import us.xnetik.tv.core.profile.ProfileScopedCredentialStore
import us.xnetik.tv.core.profile.ProfileManager
import us.xnetik.tv.data.local.ProfileDataStore
import us.xnetik.tv.data.mdblist.AndroidMdbListAuthPersistence
import us.xnetik.tv.data.mdblist.AndroidMdbListSyncStorage
import us.xnetik.tv.data.mdblist.MdbListSyncRepository
import us.xnetik.tv.data.mdblist.MdbListApiClient
import us.xnetik.tv.data.mdblist.MdbListAuthRepository
import us.xnetik.tv.data.mdblist.MdbListAuthStore
import us.xnetik.tv.data.mdblist.MdbListConfiguration
import us.xnetik.tv.data.mdblist.MdbListHttpClient
import us.xnetik.tv.data.mdblist.OkHttpMdbListEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object MdbListModule {
    @Provides
    @Singleton
    fun library(
        api: MdbListApiClient,
        sync: MdbListSyncRepository,
        auth: MdbListAuthStore,
        profiles: ProfileManager
    ) = us.xnetik.tv.data.mdblist.MdbListLibraryService(
        api, sync, auth, profiles.activeProfileId, CoroutineScope(SupervisorJob() + Dispatchers.IO)
    )

    @Provides
    @Singleton
    fun configuration(): MdbListConfiguration = MdbListConfiguration(
        clientId = BuildConfig.MDBLIST_CLIENT_ID,
        appVersion = BuildConfig.VERSION_NAME
    )

    @Provides
    @Singleton
    fun http(configuration: MdbListConfiguration): MdbListHttpClient = MdbListHttpClient(
        OkHttpMdbListEngine(
            OkHttpClient.Builder()
                .socketFactory(NoDelaySocketFactory)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .callTimeout(45, TimeUnit.SECONDS)
                .build(),
            configuration
        )
    )

    @Provides
    @Singleton
    fun authStore(persistence: AndroidMdbListAuthPersistence, profileDataStore: ProfileDataStore): MdbListAuthStore {
        val store = MdbListAuthStore(persistence)
        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            profileDataStore.activeProfileId.collect(store::selectProfile)
        }
        return store
    }

    @Provides
    @IntoSet
    fun credentialStore(store: MdbListAuthStore): ProfileScopedCredentialStore = store

    @Provides
    @Singleton
    fun auth(http: MdbListHttpClient, configuration: MdbListConfiguration, store: MdbListAuthStore) =
        MdbListAuthRepository(http, configuration, store)

    @Provides
    @Singleton
    fun api(http: MdbListHttpClient, auth: MdbListAuthRepository, store: MdbListAuthStore) =
        MdbListApiClient(http, auth, store)

    @Provides
    @Singleton
    fun syncRepository(
        storage: AndroidMdbListSyncStorage,
        auth: MdbListAuthStore,
        api: MdbListApiClient,
        profiles: ProfileManager
    ) = MdbListSyncRepository(
        storage, auth, api, profiles.activeProfileId,
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    )
}
