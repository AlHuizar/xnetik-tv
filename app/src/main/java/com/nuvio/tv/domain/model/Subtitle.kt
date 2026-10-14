package us.xnetik.tv.domain.model

import androidx.compose.runtime.Immutable
import us.xnetik.tv.ui.util.languageCodeToName

@Immutable
data class Subtitle(
    val id: String,
    val url: String,
    val lang: String,
    val addonName: String,
    val addonLogo: String?,
    val isStreamProvided: Boolean = false,
    val headers: Map<String, String>? = null
) {
    fun getDisplayLanguage(): String = languageCodeToName(lang)

    companion object {
        fun languageCodeToName(code: String): String = us.xnetik.tv.ui.util.languageCodeToName(code)
    }
}
