package org.speak.easy.settings

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent

object SettingsFeature : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = Destination.SettingsGraph.route
        ) {
            TrackScreenViewEvent(screenName = "settings screen")
        }
    }
}