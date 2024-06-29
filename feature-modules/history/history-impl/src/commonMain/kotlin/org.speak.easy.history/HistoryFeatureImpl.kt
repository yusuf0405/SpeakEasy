package org.speak.easy.history

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.history.api.HistoryFeatureApi
import org.speak.easy.ui.core.SpeakEasyTopBar
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.arrow_right
import speakeasy.ui_core.generated.resources.history
import speakeasy.ui_core.generated.resources.speak_easy

object HistoryFeatureImpl : HistoryFeatureApi {

    override fun provideHistoryRoute(): String = "history_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = provideHistoryRoute()) {
            val viewModel = koinInject<HistoryViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            Scaffold(
                topBar = {
                    SpeakEasyTopBar(
                        title = stringResource(Res.string.history),
                        contentAlignment = Alignment.Center,
                        startIcon = painterResource(Res.drawable.arrow_right),
                        onStartClick = navController::navigateUp
                    )
                }
            ) { paddings ->
                HistoryScreen(
                    modifier = Modifier.padding(top = paddings.calculateTopPadding()),
                    uiState = uiState,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}