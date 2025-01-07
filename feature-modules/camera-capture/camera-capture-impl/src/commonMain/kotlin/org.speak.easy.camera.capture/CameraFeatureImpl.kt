package org.speak.easy.camera.capture

import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import org.koin.compose.koinInject
import org.speak.easy.camera.capture.api.CameraFeatureApi
import org.speak.easy.camera.capture.di.CameraCaptureDependencies
import org.speak.easy.camera.capture.models.RecognizeTextStatus

object CameraFeatureImpl : CameraFeatureApi {

    override fun provideCameraScreenRoute(): String = "camera_feature_route"

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navController: NavHostController,
        modifier: Modifier
    ) {
        navGraphBuilder.composable(provideCameraScreenRoute()) {
            val cameraCaptureDependencies = koinInject<CameraCaptureDependencies>()
            val viewModel = koinInject<CameraViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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