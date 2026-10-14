package us.xnetik.tv.core.di

import us.xnetik.tv.core.sync.library.LibrarySyncLocalStore
import us.xnetik.tv.core.sync.library.LibrarySyncRemoteDataSource
import us.xnetik.tv.data.local.LibraryPreferences
import us.xnetik.tv.data.remote.supabase.SupabaseLibrarySyncRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LibrarySyncModule {
    @Binds
    @Singleton
    abstract fun bindLibrarySyncLocalStore(
        implementation: LibraryPreferences
    ): LibrarySyncLocalStore

    @Binds
    @Singleton
    abstract fun bindLibrarySyncRemoteDataSource(
        implementation: SupabaseLibrarySyncRemoteDataSource
    ): LibrarySyncRemoteDataSource
}
