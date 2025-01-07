package org.speak.easy.translator

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.koinInject
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.translator.api.TranslatorFeatureApi

private const val argumentKey = "arg"

object TranslatorFeatureImpl : TranslatorFeatureApi {

    override fun provideScreenRoute(): String = "translator_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = provideScreenRoute(),
            arguments = listOf(
                navArgument(argumentKey) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
            val viewModel = koinInject<TranslatorViewModel>()

            val languageFeatureApi: LanguageFeatureApi = koinInject<LanguageFeatureApi>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val sourceText by viewModel.sourceText.collectAsStateWithLifecycle()

            TranslatorScreen(
                uiState = uiState,
                sourceText = sourceText,
                languageFeatureApi = languageFeatureApi,
                onAction = viewModel::onAction,
            )
        }
    }
}