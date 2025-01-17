package org.speak.easy.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.ui.extensions.SpacerHeight
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent
import org.speak.easy.permission.api.rememberUrlLauncher
import org.speak.easy.settings.category.CategoriesList
import org.speak.easy.settings.theme.ThemeScreen
import org.speak.easy.settings.theme.ThemeViewModel
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.more

object SettingsFeature : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = Destination.SettingsScreen.route
        ) {

            val viewModel = koinInject<SettingsViewModel>()

            val urlLauncher = rememberUrlLauncher()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val launchSetting by viewModel.launchSetting.collectAsStateWithLifecycle(false)

            if (launchSetting) {
                println("openAppSettings")
                urlLauncher.openAppSettings()
                viewModel.settingsShowed()
            }

            Column(
                modifier = Modifier.background(SpeakEasyTheme.colors.backgroundPrimary)
            ) {
                CategoriesList(
                    categories = uiState.categories,
                    onClick = viewModel::onAction
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp12)
                Text(
                    text = stringResource(Res.string.more),
                    style = SpeakEasyTheme.typography.titleLarge.bold,
                    modifier = Modifier.padding(
                        start = SpeakEasyTheme.dimens.dp16
                    )
                )
                SpacerHeight(SpeakEasyTheme.dimens.dp4)
                CategoriesList(
                    categories = uiState.moreCategories,
                    onClick = viewModel::onAction
                )
            }
            TrackScreenViewEvent(screenName = "settings screen")
        }

        navGraphBuilder.navigation(
            route = Destination.SettingsGraph.route,
            startDestination = Destination.ChangeThemeScreen.route
        ) {
            navGraphBuilder.composable(
                route = Destination.ChangeThemeScreen.route,
            ) {
                val viewModel = koinInject<ThemeViewModel>()
                val theme by viewModel.themeState.collectAsStateWithLifecycle()

                ThemeScreen(
                    currentTheme = theme,
                    onThemeChange = viewModel::changeTheme
                )
            }
        }
    }
}