package org.speak.easy.camera.capture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import org.speak.easy.core.FeatureApi
import org.speak.easy.core.navigation.Destination
import org.speak.easy.core.ui.extensions.TrackScreenViewEvent
import org.speak.easy.permission.api.PermissionHandlerProvider
import org.speak.easy.permission.api.PermissionType
import org.speak.easy.permission.api.RationalPermissionDialogProvider
import org.speak.easy.permission.api.UrlLauncher
import org.speak.easy.permission.api.rememberUrlLauncher
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.camera_permission_description
import speakeasy.core.ui.generated.resources.close
import speakeasy.core.ui.generated.resources.go_to_settings
import speakeasy.core.ui.generated.resources.permission_must_be_given

object CameraFeature : FeatureApi {

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(route = Destination.CameraGraph.route) {
            val viewModel = koinInject<CameraViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            val permissionHandlerProvider = koinInject<PermissionHandlerProvider>()
            val permissionState = rememberCameraPermissionState(
                permissionHandlerProvider = permissionHandlerProvider,
                viewModel = viewModel
            )

            HandlePermissionDialogs(permissionState, viewModel)
            CameraCaptureScreen(
                modifier = modifier,
                uiState = uiState,
                onSwapLanguage = viewModel::onSwapLanguage,
                handleStatus = viewModel::handleStatus
            )
            TrackScreenViewEvent(screenName = "camera capture screen")
        }
    }
}

@Composable
private fun rememberCameraPermissionState(
    permissionHandlerProvider: PermissionHandlerProvider,
    viewModel: CameraViewModel
): CameraPermissionState {
    val showRationalDialog by viewModel.showRationalDialog.collectAsStateWithLifecycle()
    val permissionHandler = permissionHandlerProvider.get(viewModel.permissionCallback)
    val isPermissionGranted = permissionHandler.isPermissionGranted(PermissionType.CAMERA)
    var askPermission by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        askPermission = !isPermissionGranted
    }

    if (askPermission) {
        permissionHandler.askPermission(type = PermissionType.CAMERA)
        askPermission = false
    }

    return CameraPermissionState(
        isGranted = isPermissionGranted,
        urlLauncher = rememberUrlLauncher(),
        viewModel = viewModel,
        showRationalDialog = showRationalDialog
    )
}

@Composable
private fun HandlePermissionDialogs(
    state: CameraPermissionState,
    viewModel: CameraViewModel
) {
    val rationalPermissionDialogProvider = koinInject<RationalPermissionDialogProvider>()
    var launchSettings by remember { mutableStateOf(false) }

    if (launchSettings) {
        state.urlLauncher.openAppSettings()
        launchSettings = false
        viewModel.hideRationalDialog()
    }

    if (state.showRationalDialog) {
        rationalPermissionDialogProvider.get(
            title = stringResource(Res.string.permission_must_be_given),
            description = stringResource(Res.string.camera_permission_description),
            confirmText = stringResource(Res.string.go_to_settings),
            dismissText = stringResource(Res.string.close),
            onConfirm = { launchSettings = true },
            onDismiss = state.viewModel::hideRationalDialog,
            modifier = Modifier
        )
    }
}

data class CameraPermissionState(
    val isGranted: Boolean,
    val urlLauncher: UrlLauncher,
    val viewModel: CameraViewModel,
    val showRationalDialog: Boolean
)
