package us.xnetik.tv.domain.repository

import us.xnetik.tv.core.network.NetworkResult
import us.xnetik.tv.domain.model.CatalogRow
import kotlinx.coroutines.flow.Flow

interface CatalogRepository {
    fun getCatalog(
        addonBaseUrl: String,
        addonId: String,
        addonName: String,
        catalogId: String,
        catalogName: String,
        type: String,
        skip: Int = 0,
        skipStep: Int = 100,
        extraArgs: Map<String, String> = emptyMap(),
        supportsSkip: Boolean = false,
        posterScreen: us.xnetik.tv.core.poster.CustomPosterScreen = us.xnetik.tv.core.poster.CustomPosterScreen.HOME
    ): Flow<NetworkResult<CatalogRow>>
}
