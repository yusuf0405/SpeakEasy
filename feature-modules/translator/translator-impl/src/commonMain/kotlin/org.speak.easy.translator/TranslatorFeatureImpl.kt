package org.speak.easy.translator

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.translator.api.TranslatorFeatureApi
import org.speak.easy.ui.core.SpeakEasyTopBar
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.history
import speakeasy.ui_core.generated.resources.speak_easy

object TranslatorFeatureImpl : TranslatorFeatureApi {

    override fun provideTranslatorRoute(): String = "translator_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = provideTranslatorRoute()) {
            val viewModel = koinInject<TranslatorViewModel>()
            val dependencies = koinInject<TranslatorDependencies>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val bottomSheetUiState by viewModel.bottomSheetUiState.collectAsStateWithLifecycle()

            Scaffold(
                topBar = {
                    SpeakEasyTopBar(
                        title = stringResource(Res.string.speak_easy),
                        contentAlignment = Alignment.Center,
                        endIcon = painterResource(Res.drawable.history),
                        onEndClick = { navController.navigate(dependencies.getHistoryRoute()) }
                    )
                },

            ) { paddings ->
                TranslatorScreen(
                    modifier = Modifier.padding(
                        top = paddings.calculateTopPadding(),
                        bottom = paddings.calculateBottomPadding(),
                    ),
                    uiState = uiState,
                    bottomSheetUiState = bottomSheetUiState,
                    onAction = viewModel::onAction,
                )
            }
        }
    }
}