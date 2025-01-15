package org.speak.easy.camera.capture.di

import org.koin.dsl.module
import org.speak.easy.camera.capture.CameraViewModel

val cameraModule = module {
    single {
        CameraViewModel(get(), get(), get())
    }
}