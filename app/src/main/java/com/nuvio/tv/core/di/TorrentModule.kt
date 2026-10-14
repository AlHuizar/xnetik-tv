package us.xnetik.tv.core.di

import android.content.Context
import us.xnetik.tv.core.torrent.TorrentService
import us.xnetik.tv.core.torrent.TorrentSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TorrentModule {

    @Provides
    @Singleton
    fun provideTorrentSettings(
        @ApplicationContext context: Context
    ): TorrentSettings = TorrentSettings(context)

    @Provides
    @Singleton
    fun provideTorrentService(
        @ApplicationContext context: Context,
        torrentSettings: TorrentSettings
    ): TorrentService = TorrentService(context, torrentSettings)
}
