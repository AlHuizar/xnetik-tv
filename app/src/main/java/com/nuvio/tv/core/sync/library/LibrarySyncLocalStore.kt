package us.xnetik.tv.core.sync.library

import us.xnetik.tv.domain.model.LibraryDeltaApplyResult
import us.xnetik.tv.domain.model.LibraryDeltaEvent
import us.xnetik.tv.domain.model.LibrarySnapshotApplyResult
import us.xnetik.tv.domain.model.LibrarySyncState
import us.xnetik.tv.domain.model.SavedLibraryItem

interface LibrarySyncLocalStore {
    suspend fun getSyncState(profileId: Int): LibrarySyncState

    suspend fun applyRemoteSnapshot(
        profileId: Int,
        remoteItems: Collection<SavedLibraryItem>,
        cursorEventId: Long
    ): LibrarySnapshotApplyResult

    suspend fun applyRemoteDelta(
        profileId: Int,
        events: Collection<LibraryDeltaEvent>
    ): LibraryDeltaApplyResult

    suspend fun queueAllItemsForPush(profileId: Int): LibrarySyncState

    suspend fun acknowledgePush(
        profileId: Int,
        expectedMutationRevision: Long
    ): Boolean
}
