package us.xnetik.tv.core.di

import us.xnetik.tv.data.simkl.AndroidSimklAuthStorage
import us.xnetik.tv.data.simkl.AndroidSimklSyncStorage
import us.xnetik.tv.data.simkl.SimklAuthStorage
import us.xnetik.tv.data.simkl.SimklApiSyncRemote
import us.xnetik.tv.data.simkl.SimklSyncRemote
import us.xnetik.tv.data.simkl.SimklSyncStorage
import us.xnetik.tv.core.tracking.TrackingLibraryProvider
import us.xnetik.tv.core.tracking.TrackingProvider
import us.xnetik.tv.data.repository.TraktTrackingLibraryProvider
import us.xnetik.tv.data.repository.TraktTrackingProvider
import us.xnetik.tv.data.simkl.SimklLibraryService
import us.xnetik.tv.core.tracking.TrackingHistoryWriter
import us.xnetik.tv.core.tracking.TrackingProgressProvider
import us.xnetik.tv.data.repository.TraktTrackingHistoryWriter
import us.xnetik.tv.data.repository.TraktTrackingProgressProvider
import us.xnetik.tv.data.simkl.SimklTrackingHistoryWriter
import us.xnetik.tv.data.simkl.SimklTrackingProgressProvider
import us.xnetik.tv.data.simkl.SimklTrackingProvider
import us.xnetik.tv.core.profile.ProfileScopedCredentialStore
import us.xnetik.tv.data.mdblist.MdbListTrackingHistoryWriter
import us.xnetik.tv.data.mdblist.MdbListTrackingProgressProvider
import us.xnetik.tv.data.mdblist.MdbListTrackingProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TrackingModule {
    @Binds
    @IntoSet
    abstract fun bindMdbListLibrary(provider: us.xnetik.tv.data.mdblist.MdbListTrackingLibraryProvider): TrackingLibraryProvider

    @Binds
    @IntoSet
    abstract fun bindMdbListProvider(provider: MdbListTrackingProvider): TrackingProvider

    @Binds
    @IntoSet
    abstract fun bindMdbListProgress(provider: MdbListTrackingProgressProvider): TrackingProgressProvider

    @Binds
    @IntoSet
    abstract fun bindMdbListHistory(writer: MdbListTrackingHistoryWriter): TrackingHistoryWriter

    @Binds
    @Singleton
    abstract fun bindSimklAuthStorage(storage: AndroidSimklAuthStorage): SimklAuthStorage

    @Binds
    @IntoSet
    abstract fun bindSimklProfileScopedCredentialStore(
        storage: AndroidSimklAuthStorage
    ): ProfileScopedCredentialStore

    @Binds
    @Singleton
    abstract fun bindSimklSyncStorage(storage: AndroidSimklSyncStorage): SimklSyncStorage

    @Binds
    @Singleton
    abstract fun bindSimklSyncRemote(remote: SimklApiSyncRemote): SimklSyncRemote

    @Binds
    @IntoSet
    abstract fun bindTraktTrackingLibraryProvider(
        provider: TraktTrackingLibraryProvider
    ): TrackingLibraryProvider

    @Binds
    @IntoSet
    abstract fun bindSimklTrackingLibraryProvider(
        provider: SimklLibraryService
    ): TrackingLibraryProvider

    @Binds
    @IntoSet
    abstract fun bindTraktTrackingProgressProvider(
        provider: TraktTrackingProgressProvider
    ): TrackingProgressProvider

    @Binds
    @IntoSet
    abstract fun bindSimklTrackingProgressProvider(
        provider: SimklTrackingProgressProvider
    ): TrackingProgressProvider

    @Binds
    @IntoSet
    abstract fun bindTraktTrackingHistoryWriter(
        writer: TraktTrackingHistoryWriter
    ): TrackingHistoryWriter

    @Binds
    @IntoSet
    abstract fun bindSimklTrackingHistoryWriter(
        writer: SimklTrackingHistoryWriter
    ): TrackingHistoryWriter

    @Binds
    @IntoSet
    abstract fun bindTraktTrackingProvider(
        provider: TraktTrackingProvider
    ): TrackingProvider

    @Binds
    @IntoSet
    abstract fun bindSimklTrackingProvider(
        provider: SimklTrackingProvider
    ): TrackingProvider
}
