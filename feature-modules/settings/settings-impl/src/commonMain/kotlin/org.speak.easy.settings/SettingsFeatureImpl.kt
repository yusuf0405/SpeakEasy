package org.speak.easy.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.speak.easy.settings.api.SettingsFeatureApi
import org.speak.easy.ui.core.theme.SpeakEasyTheme

object SettingsFeatureImpl : SettingsFeatureApi {

    override fun provideScreenRoute(): String = "settings_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(provideScreenRoute()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .background(SpeakEasyTheme.colors.backgroundPrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Settings",
                    style = SpeakEasyTheme.typography.titleLarge.bold,
                    color = SpeakEasyTheme.colors.textPrimary
                )
            }
        }
    }
}