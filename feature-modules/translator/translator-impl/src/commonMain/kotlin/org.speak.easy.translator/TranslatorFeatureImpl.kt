package org.speak.easy.translator

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.translator.api.TranslatorFeatureApi

object TranslatorFeatureImpl : TranslatorFeatureApi {

    override fun provideScreenRoute(): String = "translator_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = provideScreenRoute()) {
            val viewModel = koinInject<TranslatorViewModel>()
            val languageFeatureApi: LanguageFeatureApi = koinInject<LanguageFeatureApi>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            TranslatorScreen(
                uiState = uiState,
                languageFeatureApi = languageFeatureApi,
                onAction = viewModel::onAction,
            )
        }
    }
}