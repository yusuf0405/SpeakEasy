package org.speak.easy.permission

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandler
import org.speak.easy.permission.api.PermissionStatus
import org.speak.easy.permission.api.PermissionType
import java.util.concurrent.atomic.AtomicBoolean

@Composable
actual fun createPermissionsManager(callback: PermissionCallback): PermissionsManager {
    return remember { PermissionsManager(callback) }
}

actual class PermissionsManager actual constructor(
    private val callback: PermissionCallback
) : PermissionHandler {

    private val isCameraPermissionAsked = AtomicBoolean(false)

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun askPermission(type: PermissionType) {
        when (type) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = cameraPermissionState()
                if (isCameraPermissionAsked.get() && !cameraPermissionState.allPermissionsGranted) {
                    callback.onPermissionStatus(type, PermissionStatus.DENIED)
                    return
                }
                if (isCameraPermissionAsked.compareAndSet(false, true)) {
                    cameraPermissionState.permissions.forEach { permission ->
                        GrantPermission(permission, type)
                    }
                }
            }

            PermissionType.RECORD_AUDIO -> {
                val recordAudioPermissionState = recordAudioPermissionState()
                GrantPermission(recordAudioPermissionState, type)
            }

            PermissionType.GALLERY -> {
                callback.onPermissionStatus(
                    type, PermissionStatus.GRANTED
                )
            }
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun GrantPermission(state: PermissionState, permission: PermissionType) {
        val lifecycleOwner = LocalLifecycleOwner.current
        LaunchedEffect(state) {
            val permissionResult = state.status

            if (!permissionResult.isGranted) {
                if (permissionResult.shouldShowRationale) {
                    callback.onPermissionStatus(
                        permission, PermissionStatus.SHOW_RATIONAL
                    )
                } else {
                    lifecycleOwner.lifecycleScope.launch {
                        state.launchPermissionRequest()
                    }
                }
            } else {
                callback.onPermissionStatus(
                    permission, PermissionStatus.GRANTED
                )
            }
        }
    }


    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    override fun isPermissionGranted(permission: PermissionType): Boolean {
        return when (permission) {
            PermissionType.CAMERA -> {
                val cameraPermissionState = cameraPermissionState()
                cameraPermissionState.allPermissionsGranted
            }

            PermissionType.RECORD_AUDIO -> {
                val recordAudioPermissionState = recordAudioPermissionState()
                recordAudioPermissionState.status.isGranted
            }

            PermissionType.GALLERY -> {
                true
            }
        }
    }

    @Composable
    override fun launchSettings() {
        val context = LocalContext.current
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        ).also {
            context.startActivity(it)
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun cameraPermissionState(): MultiplePermissionsState {
        return rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        )
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun recordAudioPermissionState(): PermissionState {
        return rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    }
}