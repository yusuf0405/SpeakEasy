package di

import navigation.BottomNavigationItem
import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.speak.easy.core.ClipboardCopyManager
import org.speak.easy.core.TextSharingManager
import org.speak.easy.core.provideClipboardUpdateManager
import org.speak.easy.core.provideTextShareManager
import org.speak.easy.domain.di.domainModule
import org.speak.easy.history.HistoryFeatureImpl
import org.speak.easy.history.di.historyModule
import org.speak.easy.languages.LanguageFeatureImpl
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.languages.di.languagesModule
import org.speak.easy.settings.SettingsFeatureImpl
import org.speak.easy.speech.speechFeatureModule
import org.speak.easy.translator.TranslatorDependencies
import org.speak.easy.translator.TranslatorFeatureImpl
import org.speak.easy.translator.di.translatorModule
import org.speak.easy.ui.components.di.uiComponentsModule
import speakeasy.ui_core.generated.resources.Res
import speakeasy.ui_core.generated.resources.check_icon
import speakeasy.ui_core.generated.resources.history
import speakeasy.ui_core.generated.resources.main
import speakeasy.ui_core.generated.resources.mdi_translate
import speakeasy.ui_core.generated.resources.settings

val FEATURE_API_MODULES: StringQualifier = qualifier(name = "featureApiModules")
val BOTTOM_NAVIGATION_ITEMS: StringQualifier = qualifier(name = "BOTTOM_NAVIGATION_ITEMS")

fun getAppModules(): List<Module> = listOf(
    translatorModule,
    historyModule,
    speechFeatureModule,
    featureApiModule,
    featureDependencyModule,
    domainModule,
    coreModule,
    uiComponentsModule,
    languagesModule
)

private val coreModule = module {
    factory<TextSharingManager> { provideTextShareManager(get()) }
    factory<ClipboardCopyManager> { provideClipboardUpdateManager(get()) }
}

private val featureDependencyModule = module {
    single<TranslatorDependencies> {
        object : TranslatorDependencies {
            override fun getHistoryRoute(): String = ScreenRoutesProvider.getHistoryRoute()
        }
    }
}
private val featureApiModule = module {
    single(qualifier = FEATURE_API_MODULES) {
        listOf(
            HistoryFeatureImpl,
            TranslatorFeatureImpl,
            SettingsFeatureImpl
        )
    }
    single<LanguageFeatureApi> { LanguageFeatureImpl }

    single(qualifier = BOTTOM_NAVIGATION_ITEMS) {
        listOf(
            BottomNavigationItem(
                icon = Res.drawable.history,
                title = Res.string.history,
                route = ScreenRoutesProvider.getHistoryRoute()
            ),
            BottomNavigationItem(
                icon = Res.drawable.mdi_translate,
                title = Res.string.main,
                route = ScreenRoutesProvider.getTranslatorRoute()
            ),
            BottomNavigationItem(
                icon = Res.drawable.settings,
                title = Res.string.settings,
                route = ScreenRoutesProvider.getSettingsRoute()
            )
        )
    }
}