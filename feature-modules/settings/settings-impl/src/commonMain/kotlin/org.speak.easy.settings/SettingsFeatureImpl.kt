package org.speak.easy.settings

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.speak.easy.settings.api.SettingsFeatureApi

object SettingsFeatureImpl : SettingsFeatureApi {

    override fun provideScreenRoute(): String = "settings_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(provideScreenRoute()) {

        }
    }
}