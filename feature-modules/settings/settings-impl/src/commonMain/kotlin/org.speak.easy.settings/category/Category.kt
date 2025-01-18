package org.speak.easy.settings.category

import androidx.compose.runtime.Immutable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

enum class CategoryType {
    LANGUAGE,
    THEME,
    CLEAN_HISTORY,
    HELP_SUPPORT,
    ABOUT_APP,
    RATE_APP,
}

@Immutable
data class Category(
    val title: StringResource,
    val description: StringResource?,
    val resource: DrawableResource,
    val type: CategoryType,
    val isRedContent: Boolean = false
)