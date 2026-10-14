package us.xnetik.tv.core.di

import us.xnetik.tv.data.repository.AddonRepositoryImpl
import us.xnetik.tv.data.repository.CatalogRepositoryImpl
import us.xnetik.tv.data.repository.LibraryRepositoryImpl
import us.xnetik.tv.data.repository.MetaRepositoryImpl
import us.xnetik.tv.data.repository.StreamRepositoryImpl
import us.xnetik.tv.data.repository.SubtitleRepositoryImpl
import us.xnetik.tv.data.repository.SyncRepositoryImpl
import us.xnetik.tv.data.repository.WatchProgressRepositoryImpl
import us.xnetik.tv.domain.repository.AddonRepository
import us.xnetik.tv.domain.repository.CatalogRepository
import us.xnetik.tv.domain.repository.LibraryRepository
import us.xnetik.tv.domain.repository.MetaRepository
import us.xnetik.tv.domain.repository.StreamRepository
import us.xnetik.tv.domain.repository.SubtitleRepository
import us.xnetik.tv.domain.repository.SyncRepository
import us.xnetik.tv.domain.repository.WatchProgressRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAddonRepository(impl: AddonRepositoryImpl): AddonRepository

    @Binds
    @Singleton
    abstract fun bindCatalogRepository(impl: CatalogRepositoryImpl): CatalogRepository

    @Binds
    @Singleton
    abstract fun bindLibraryRepository(impl: LibraryRepositoryImpl): LibraryRepository

    @Binds
    @Singleton
    abstract fun bindMetaRepository(impl: MetaRepositoryImpl): MetaRepository

    @Binds
    @Singleton
    abstract fun bindStreamRepository(impl: StreamRepositoryImpl): StreamRepository

    @Binds
    @Singleton
    abstract fun bindSubtitleRepository(impl: SubtitleRepositoryImpl): SubtitleRepository

    @Binds
    @Singleton
    abstract fun bindSyncRepository(impl: SyncRepositoryImpl): SyncRepository

    @Binds
    @Singleton
    abstract fun bindWatchProgressRepository(impl: WatchProgressRepositoryImpl): WatchProgressRepository
}
