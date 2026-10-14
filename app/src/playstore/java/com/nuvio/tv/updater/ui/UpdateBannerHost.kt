package us.xnetik.tv.updater.ui

import androidx.compose.runtime.Composable
import us.xnetik.tv.updater.UpdateUiState

@Composable
fun UpdateBannerHost(
    state: UpdateUiState,
    onDismissBanner: () -> Unit,
    onDownload: () -> Unit,
    onInstall: () -> Unit,
    onDismissUnknownSources: () -> Unit,
    onOpenUnknownSources: () -> Unit,
    onFeedbackShown: () -> Unit,
    content: @Composable () -> Unit
) {
    content()
}
