package org.speak.easy.di

import org.koin.dsl.module
import org.speak.easy.camera.capture.di.CameraCaptureDependencies
import org.speak.easy.translator.api.SourceTextManager

internal val featureDependenciesModule = module {
    single<CameraCaptureDependencies> {
        object : CameraCaptureDependencies {
            override fun getTranslatorScreenRoute(): String =
                ScreenRoutesProvider.getTranslatorRoute()
        }
    }
}