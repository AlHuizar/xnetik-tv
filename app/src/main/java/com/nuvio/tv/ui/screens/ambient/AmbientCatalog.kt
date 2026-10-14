package us.xnetik.tv.ui.screens.ambient

import us.xnetik.tv.domain.model.Addon
import us.xnetik.tv.domain.model.CatalogDescriptor
import us.xnetik.tv.domain.model.catalogRowLegacyKey

data class AmbientCatalog(
    val key: String,
    val title: String,
    val source: String,
    val addon: Addon,
    val catalog: CatalogDescriptor
)

internal fun List<Addon>.ambientCatalogs(): List<AmbientCatalog> = flatMap { addon ->
    addon.catalogs
        .filterNot { catalog -> catalog.extra.any { it.isRequired } }
        .map { catalog ->
            AmbientCatalog(
                key = catalogRowLegacyKey(addon.id, catalog.apiType, catalog.id),
                title = catalog.name,
                source = addon.displayName,
                addon = addon,
                catalog = catalog
            )
        }
}
