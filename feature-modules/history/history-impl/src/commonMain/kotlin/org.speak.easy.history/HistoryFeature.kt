package org.speak.easy.history

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent

object HistoryFeature : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = Destination.HistoryGraph.route
        ) {
            val viewModel = koinInject<HistoryViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            HistoryScreen(
                uiState = uiState,
                onAction = viewModel::onAction
            )
            TrackScreenViewEvent(screenName = "history screen")
        }
    }
}