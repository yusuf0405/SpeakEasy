package org.speak.easy.settings

import androidx.compose.runtime.Immutable
import org.speak.easy.settings.category.Category

@Immutable
data class SettingsUiState(
    val categories: List<Category>,
    val moreCategories: List<Category>,
) {

    companion object {
        val default = SettingsUiState(
            categories = emptyList(),
            moreCategories = emptyList()
        )
    }
}