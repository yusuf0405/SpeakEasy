package org.speak.easy.camera.capture

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
import org.speak.easy.camera.capture.api.CameraFeatureApi
import org.speak.easy.camera.capture.di.CameraCaptureDependencies
import org.speak.easy.camera.capture.models.RecognizeTextStatus
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandlerProvider
import org.speak.easy.permission.api.PermissionStatus
import org.speak.easy.permission.api.PermissionType
import org.speak.easy.permission.api.RationalPermissionDialogProvider
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.camera_permission_description
import speakeasy.core.ui.generated.resources.close
import speakeasy.core.ui.generated.resources.go_to_settings
import speakeasy.core.ui.generated.resources.permission_must_be_given

object CameraFeatureImpl : CameraFeatureApi {

    override fun provideCameraScreenRoute(): String = "camera_feature_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(provideCameraScreenRoute()) {
            val cameraCaptureDependencies = koinInject<CameraCaptureDependencies>()
            val permissionHandlerProvider = koinInject<PermissionHandlerProvider>()
            val rationalPermissionDialogProvider = koinInject<RationalPermissionDialogProvider>()

            val viewModel = koinInject<CameraViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            var permissionRationalDialog by remember { mutableStateOf(false) }
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
            val isPermissionGranted = permissionHandler.isPermissionGranted(PermissionType.CAMERA)
            if (!isPermissionGranted) {
                permissionHandler.askPermission(PermissionType.CAMERA)
            }

            if (launchSettings) {
                permissionHandler.launchSettings()
                launchSettings = false
                permissionRationalDialog = false
            }

            if (permissionRationalDialog) {
                rationalPermissionDialogProvider.get(
                    title = stringResource(Res.string.permission_must_be_given),
                    description = stringResource(Res.string.camera_permission_description),
                    confirmText = stringResource(Res.string.go_to_settings),
                    dismissText = stringResource(Res.string.close),
                    onConfirm = { launchSettings = true },
                    onDismiss = { permissionRationalDialog = false },
                    modifier = Modifier
                )
            }

            CameraCaptureScreen(
                modifier = Modifier,
                uiState = uiState,
                onSwapLanguage = viewModel::onSwapLanguage,
                handleStatus = { status ->
                    when (status) {
                        RecognizeTextStatus.Initial -> Unit
                        RecognizeTextStatus.Progress -> Unit
                        is RecognizeTextStatus.Success -> {
                            viewModel.setSourceText(status.text)
                            navController.navigate(cameraCaptureDependencies.getTranslatorScreenRoute())
                        }

                        RecognizeTextStatus.Error -> Unit
                    }
                }
            )
        }
    }
}