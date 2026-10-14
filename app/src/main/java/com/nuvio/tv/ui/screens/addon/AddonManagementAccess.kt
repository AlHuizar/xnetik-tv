package us.xnetik.tv.ui.screens.addon

import us.xnetik.tv.core.server.AddonWebConfigMode
import us.xnetik.tv.domain.model.ExperienceMode
import us.xnetik.tv.domain.model.UserProfile

internal object AddonManagementAccess {

    fun isReadOnly(profile: UserProfile?): Boolean {
        return profile?.let { !it.isPrimary && it.usesPrimaryAddons } == true
    }

    fun webConfigMode(
        profile: UserProfile?,
        experienceMode: ExperienceMode = ExperienceMode.ADVANCED
    ): AddonWebConfigMode {
        return when {
            isReadOnly(profile) -> AddonWebConfigMode.COLLECTIONS_ONLY
            experienceMode == ExperienceMode.ESSENTIAL -> AddonWebConfigMode.ADDONS_ONLY
            else -> AddonWebConfigMode.FULL
        }
    }
}
