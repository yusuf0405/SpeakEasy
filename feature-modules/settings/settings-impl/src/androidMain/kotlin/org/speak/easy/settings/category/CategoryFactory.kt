package org.speak.easy.settings.category

import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.about
import speakeasy.core.ui.generated.resources.change_language
import speakeasy.core.ui.generated.resources.help
import speakeasy.core.ui.generated.resources.history
import speakeasy.core.ui.generated.resources.language
import speakeasy.core.ui.generated.resources.change_language_in_app
import speakeasy.core.ui.generated.resources.theme
import speakeasy.core.ui.generated.resources.trash
import speakeasy.core.ui.generated.resources.dark_theme
import speakeasy.core.ui.generated.resources.apply_dark_theme
import speakeasy.core.ui.generated.resources.clear_your_transfer_history
import speakeasy.core.ui.generated.resources.help_support
import speakeasy.core.ui.generated.resources.about_app
import speakeasy.core.ui.generated.resources.rate_app

actual class CategoryFactory {

    actual fun categories(): List<Category> = listOf(
        Category(
            title = Res.string.language,
            description = Res.string.change_language_in_app,
            resource = Res.drawable.change_language,
            type = CategoryType.LANGUAGE
        ),
        Category(
            title = Res.string.dark_theme,
            description = Res.string.apply_dark_theme,
            resource = Res.drawable.theme,
            type = CategoryType.THEME
        ),
        Category(
            title = Res.string.history,
            description = Res.string.clear_your_transfer_history,
            resource = Res.drawable.trash,
            isRedContent = true,
            type = CategoryType.CLEAN_HISTORY
        )
    )

    actual fun moreCategories(): List<Category> = listOf(
        Category(
            title = Res.string.rate_app,
            description = null,
            resource = Res.drawable.rate_app,
            type = CategoryType.RATE_APP
        ),
        Category(
            title = Res.string.help_support,
            description = null,
            resource = Res.drawable.help,
            type = CategoryType.HELP_SUPPORT
        ),
        Category(
            title = Res.string.about_app,
            description = null,
            resource = Res.drawable.about,
            type = CategoryType.ABOUT_APP
        )
    )
}