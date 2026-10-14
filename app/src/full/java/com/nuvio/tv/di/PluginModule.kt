package us.xnetik.tv.di

import us.xnetik.tv.core.auth.AuthManager
import us.xnetik.tv.core.plugin.PluginManager
import us.xnetik.tv.core.plugin.PluginRuntime
import us.xnetik.tv.core.plugin.cloudstream.ExternalExtensionLoader
import us.xnetik.tv.core.plugin.cloudstream.ExternalExtensionRunner
import us.xnetik.tv.core.plugin.cloudstream.ExternalRepoParser
import us.xnetik.tv.core.sync.PluginSyncService
import us.xnetik.tv.data.local.PluginDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PluginModule {

    @Provides
    @Singleton
    fun providePluginRuntime(): PluginRuntime {
        return PluginRuntime()
    }

    @Provides
    @Singleton
    fun providePluginManager(
        dataStore: PluginDataStore,
        runtime: PluginRuntime,
        pluginSyncService: PluginSyncService,
        authManager: AuthManager,
        externalRepoParser: ExternalRepoParser,
        externalExtensionLoader: ExternalExtensionLoader,
        externalExtensionRunner: ExternalExtensionRunner
    ): PluginManager {
        return PluginManager(
            dataStore, runtime, pluginSyncService, authManager,
            externalRepoParser, externalExtensionLoader, externalExtensionRunner
        )
    }
}
