package us.xnetik.tv.ui.screens.library

import us.xnetik.tv.core.auth.AuthManager
import us.xnetik.tv.core.profile.ProfileManager
import us.xnetik.tv.core.tracking.TrackingLibraryProvider
import us.xnetik.tv.core.tracking.TrackingLibrarySorter
import us.xnetik.tv.core.tracking.TrackingLibraryProviderRegistry
import us.xnetik.tv.core.tracking.TrackingListManagementCapabilities
import us.xnetik.tv.core.tracking.TrackingListManager
import us.xnetik.tv.core.tracking.TrackingProviderId
import us.xnetik.tv.data.local.DebridSettingsDataStore
import us.xnetik.tv.data.local.LayoutPreferenceDataStore
import us.xnetik.tv.data.local.LibraryPreferences
import us.xnetik.tv.data.local.WatchedSeriesStateHolder
import us.xnetik.tv.domain.model.AuthState
import us.xnetik.tv.domain.model.DebridSettings
import us.xnetik.tv.domain.model.LibraryEntry
import us.xnetik.tv.domain.model.LibraryListPrivacy
import us.xnetik.tv.domain.model.LibraryListTab
import us.xnetik.tv.domain.model.LibrarySourceMode
import us.xnetik.tv.domain.repository.LibraryRepository
import us.xnetik.tv.domain.repository.WatchProgressRepository
import io.mockk.every
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

internal class LibraryViewModelTestFixture(sorter: TrackingLibrarySorter? = null) {
    val profile = MutableStateFlow(1)
    val source = MutableStateFlow(LibrarySourceMode.MDBLIST)
    val authenticated = MutableStateFlow(true)
    val tab = LibraryListTab("mdblist:list:7", "Favourites", LibraryListTab.Type.PERSONAL,
        description = "Existing provider description", privacy = LibraryListPrivacy.PRIVATE)
    val tabs = MutableStateFlow(listOf(tab))
    val items = MutableStateFlow(emptyList<LibraryEntry>())
    val persistedSort = MutableStateFlow<String?>(null)
    val repository = mockk<LibraryRepository>(relaxed = true) {
        every { sourceMode } returns source
        every { isSyncing } returns flowOf(false)
        every { libraryItems } returns items
        every { listTabs } returns tabs
    }
    private val manager = mockk<TrackingListManager> {
        every { capabilities } returns TrackingListManagementCapabilities(listOf(LibraryListPrivacy.PRIVATE, LibraryListPrivacy.PUBLIC))
    }
    val provider = mockk<TrackingLibraryProvider> {
        every { providerId } returns TrackingProviderId.MDBLIST
        every { isAuthenticated } returns authenticated
        every { listManager } returns manager
        every { listSorter } returns sorter
    }
    val viewModel = LibraryViewModel(
        libraryRepository = repository, cloudLibraryRepository = mockk(relaxed = true), cloudPlaybackSessionStore = mockk(),
        externalPlaybackTracker = mockk(), playerSettingsDataStore = mockk(), metaRepository = mockk(),
        debridSettingsDataStore = mockk<DebridSettingsDataStore> { every { settings } returns flowOf(DebridSettings(cloudLibraryEnabled = false)) },
        layoutPreferenceDataStore = mockk<LayoutPreferenceDataStore> {
            every { posterCardWidthDp } returns flowOf(126)
            every { posterCardCornerRadiusDp } returns flowOf(12)
            every { customPosterUrlPattern } returns flowOf("")
            every { customPosterEnabledScreens } returns flowOf(us.xnetik.tv.core.poster.CustomPosterScreen.ALL)
        },
        libraryPreferences = mockk<LibraryPreferences>(relaxed = true) {
            every { sortOption } returns persistedSort
            coEvery { setSortOption(any()) } coAnswers { persistedSort.value = firstArg() }
            every { lastSelectedList } returns flowOf(null)
            every { lastSelectedType } returns flowOf(null)
        },
        authManager = mockk<AuthManager> { every { authState } returns MutableStateFlow(AuthState.SignedOut) },
        trackingProviderRegistry = TrackingLibraryProviderRegistry(setOf(provider)),
        watchProgressRepository = mockk<WatchProgressRepository> { every { observeWatchedMovieIds() } returns flowOf(emptySet()) },
        watchedSeriesStateHolder = mockk<WatchedSeriesStateHolder> { every { fullyWatchedSeriesIds } returns MutableStateFlow(emptySet()) },
        profileManager = mockk<ProfileManager> { every { activeProfileId } returns profile },
        posterOptions = mockk(relaxed = true), context = mockk(relaxed = true)
    )
}
