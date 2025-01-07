package org.speak.easy.camera.capture.api

import org.speak.easy.core.FeatureApi

interface CameraFeatureApi : FeatureApi {
    fun provideCameraScreenRoute(): String
}