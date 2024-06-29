import androidx.compose.ui.window.ComposeUIViewController
import di.getAppModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.di.getSharedModule
import platform.CoreText.kThirdWidthTextSelector
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    val d = kThirdWidthTextSelector
    App()
}.apply {
    initKoin(this)
}

fun initKoin(viewController: UIViewController) {
    val koinApp = startKoin {
        modules(getAppModules() + getSharedModule() + module {
            single { PlatformConfiguration(viewController) }
        })
    }.koin
}