package di

import org.speak.easy.camera.capture.CameraFeatureImpl
import org.speak.easy.history.HistoryFeatureImpl
import org.speak.easy.settings.SettingsFeatureImpl
import org.speak.easy.translator.TranslatorFeatureImpl

object ScreenRoutesProvider {

    fun getTranslatorRoute(): String = TranslatorFeatureImpl.provideScreenRoute()

    fun getHistoryRoute(): String = HistoryFeatureImpl.provideScreenRoute()

    fun getSettingsRoute(): String = SettingsFeatureImpl.provideScreenRoute()

    fun getCameraCaptureRoute(): String = CameraFeatureImpl.provideCameraScreenRoute()
}