package org.speak.easy

import androidx.compose.ui.window.ComposeUIViewController
import org.speak.easy.di.getAppModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.di.getSharedModule
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    App()
}.apply {
    initKoin(this)
}

fun initKoin(viewController: UIViewController) {
    startKoin {
        modules(getAppModules() + getSharedModule() + module {
            single { PlatformConfiguration(viewController) }
        })
    }.koin
}