package org.speak.easy.translator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandler
import org.speak.easy.permission.api.PermissionHandlerProvider
import org.speak.easy.permission.api.PermissionStatus
import org.speak.easy.permission.api.PermissionType
import org.speak.easy.permission.api.RationalPermissionDialogProvider
import org.speak.easy.permission.api.rememberUrlLauncher
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.close
import speakeasy.core.ui.generated.resources.go_to_settings
import speakeasy.core.ui.generated.resources.microphone_permission_description
import speakeasy.core.ui.generated.resources.permission_must_be_given

private const val ARGUMENT_KEY = "arg"

object TranslatorFeature : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(
            route = Destination.TranslatorGraph.route,
            arguments = listOf(
                navArgument(ARGUMENT_KEY) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            )
        ) {
            TranslatorScreenContainer()
        }
    }
}

@Composable
private fun TranslatorScreenContainer() {
    val viewModel = koinInject<TranslatorViewModel>()
    val languageFeatureApi: LanguageFeatureApi = koinInject()
    val permissionHandlerProvider = koinInject<PermissionHandlerProvider>()
    val rationalPermissionDialogProvider = koinInject<RationalPermissionDialogProvider>()

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sourceText by viewModel.sourceText.collectAsStateWithLifecycle()

    val (askPermission, setAskPermission) = remember { mutableStateOf(false) }
    val (launchSettings, setLaunchSettings) = remember { mutableStateOf(false) }
    val (permissionRationalDialog, setPermissionRationalDialog) = remember { mutableStateOf(false) }

    val callback = object : PermissionCallback {
        override fun onPermissionStatus(permissionType: PermissionType, status: PermissionStatus) {
            if (status != PermissionStatus.GRANTED) {
                setPermissionRationalDialog(true)
            }
        }

    }
    val permissionHandler = permissionHandlerProvider.get(callback)
    val isPermissionGranted = permissionHandler.isPermissionGranted(PermissionType.RECORD_AUDIO)

    HandlePermissionRequests(
        askPermission = askPermission,
        setAskPermission = setAskPermission,
        launchSettings = launchSettings,
        setLaunchSettings = setLaunchSettings,
        permissionHandler = permissionHandler
    )

    ShowPermissionDialog(
        permissionRationalDialog = permissionRationalDialog,
        setPermissionRationalDialog = setPermissionRationalDialog,
        setLaunchSettings = setLaunchSettings,
        rationalPermissionDialogProvider = rationalPermissionDialogProvider
    )

    TranslatorScreen(
        uiState = uiState,
        sourceText = sourceText,
        languageFeatureApi = languageFeatureApi,
        onAction = { action ->
            if (action == TranslatorScreenAction.OnSpeakClick && !isPermissionGranted) {
                setAskPermission(true)
            } else {
                viewModel.onAction(action)
            }
        }
    )
    TrackScreenViewEvent(screenName = "translator screen")
}

@Composable
private fun HandlePermissionRequests(
    askPermission: Boolean,
    setAskPermission: (Boolean) -> Unit,
    launchSettings: Boolean,
    setLaunchSettings: (Boolean) -> Unit,
    permissionHandler: PermissionHandler
) {
    val urlLauncher = rememberUrlLauncher()

    if (askPermission) {
        permissionHandler.askPermission(PermissionType.RECORD_AUDIO)
        setAskPermission(false)
    }

    if (launchSettings) {
        urlLauncher.openAppSettings()
        setLaunchSettings(false)
    }
}

@Composable
private fun ShowPermissionDialog(
    permissionRationalDialog: Boolean,
    setPermissionRationalDialog: (Boolean) -> Unit,
    setLaunchSettings: (Boolean) -> Unit,
    rationalPermissionDialogProvider: RationalPermissionDialogProvider
) {
    if (permissionRationalDialog) {
        rationalPermissionDialogProvider.get(
            title = stringResource(Res.string.permission_must_be_given),
            description = stringResource(Res.string.microphone_permission_description),
            confirmText = stringResource(Res.string.go_to_settings),
            dismissText = stringResource(Res.string.close),
            onConfirm = { setLaunchSettings(true) },
            onDismiss = { setPermissionRationalDialog(false) },
            modifier = Modifier
        )
    }
}
