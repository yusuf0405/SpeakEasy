package org.speak.easy.translator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandlerProvider
import org.speak.easy.permission.api.PermissionStatus
import org.speak.easy.permission.api.PermissionType
import org.speak.easy.permission.api.RationalPermissionDialogProvider
import org.speak.easy.translator.api.TranslatorFeatureApi
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.microphone_permission_description
import speakeasy.ui_core.generated.resources.close
import speakeasy.ui_core.generated.resources.go_to_settings
import speakeasy.ui_core.generated.resources.permission_must_be_given

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
            val permissionHandlerProvider = koinInject<PermissionHandlerProvider>()
            val rationalPermissionDialogProvider = koinInject<RationalPermissionDialogProvider>()
            val languageFeatureApi: LanguageFeatureApi = koinInject<LanguageFeatureApi>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val sourceText by viewModel.sourceText.collectAsStateWithLifecycle()

            var permissionRationalDialog by remember { mutableStateOf(false) }
            var askPermission by remember { mutableStateOf(false) }
            var launchSettings by remember { mutableStateOf(false) }

            val callback = object : PermissionCallback {
                override fun onPermissionStatus(
                    permissionType: PermissionType,
                    status: PermissionStatus
                ) {
                    when (status) {
                        PermissionStatus.GRANTED -> Unit
                        else -> permissionRationalDialog = true
                    }
                }
            }
            val permissionHandler = permissionHandlerProvider.get(callback)
            val isPermissionGranted = permissionHandler.isPermissionGranted(PermissionType.RECORD_AUDIO)

            if (askPermission) {
                permissionHandler.askPermission(PermissionType.RECORD_AUDIO)
                askPermission = false
            }

            if (launchSettings) {
                permissionHandler.launchSettings()
                launchSettings = false
                permissionRationalDialog = false
            }

            if (permissionRationalDialog) {
                rationalPermissionDialogProvider.get(
                    title = stringResource(Res.string.permission_must_be_given),
                    description = stringResource(Res.string.microphone_permission_description),
                    confirmText = stringResource(Res.string.go_to_settings),
                    dismissText = stringResource(Res.string.close),
                    onConfirm = { launchSettings = true },
                    onDismiss = { permissionRationalDialog = false },
                    modifier = Modifier
                )
            }

            TranslatorScreen(
                uiState = uiState,
                sourceText = sourceText,
                languageFeatureApi = languageFeatureApi,
                onAction = { action ->
                    if (action == TranslatorScreenAction.OnSpeakClick && !isPermissionGranted) {
                        askPermission = true
                    } else {
                        viewModel.onAction(action)
                    }
                }
            )
        }
    }
}