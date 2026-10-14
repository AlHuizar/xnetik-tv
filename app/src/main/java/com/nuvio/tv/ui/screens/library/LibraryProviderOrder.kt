package us.xnetik.tv.ui.screens.library

import us.xnetik.tv.domain.model.LibraryEntry
import us.xnetik.tv.domain.model.LibrarySourceMode

data class LibraryProviderOrder(
    val source: LibrarySourceMode,
    val listKey: String,
    val sortOption: LibrarySortOption,
    val ranks: Map<String, Int>
) {
    fun comparator(): Comparator<LibraryEntry> = compareBy<LibraryEntry> {
        ranks["${it.type}:${it.id}"] ?: Int.MAX_VALUE
    }.thenBy { it.id }
}
