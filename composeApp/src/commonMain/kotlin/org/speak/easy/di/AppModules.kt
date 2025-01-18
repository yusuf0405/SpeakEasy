package org.speak.easy.di

import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.speak.easy.ApplicationViewModel
import org.speak.easy.camera.capture.CameraFeature
import org.speak.easy.camera.capture.di.cameraModule
import org.speak.easy.core.AppLauncher
import org.speak.easy.core.AppRater
import org.speak.easy.core.ClipboardCopyManager
import org.speak.easy.core.TextSharingManager
import org.speak.easy.core.VersionInfo
import org.speak.easy.core.provideClipboardUpdateManager
import org.speak.easy.core.provideTextShareManager
import org.speak.easy.domain.di.domainModule
import org.speak.easy.history.HistoryFeature
import org.speak.easy.history.di.historyModule
import org.speak.easy.languages.LanguageFeature
import org.speak.easy.languages.api.LanguageFeatureApi
import org.speak.easy.languages.di.languagesModule
import org.speak.easy.core.navigation.di.navigationModule
import org.speak.easy.permission.di.permissionsModule
import org.speak.easy.settings.SettingsFeature
import org.speak.easy.settings.di.settingsModule
import org.speak.easy.speech.speechFeatureModule
import org.speak.easy.translator.TranslatorFeature
import org.speak.easy.translator.di.translatorModule
import org.speak.easy.ui.components.di.uiComponentsModule

val FEATURE_API_MODULES: StringQualifier = qualifier(name = "featureApiModules")

fun getAppModules(): List<Module> = listOf(
    navigationModule,
    translatorModule,
    historyModule,
    speechFeatureModule,
    featureApiModule,
    domainModule,
    coreModule,
    uiComponentsModule,
    languagesModule,
    cameraModule,
    permissionsModule,
    settingsModule,
)

private val coreModule = module {
    factory<TextSharingManager> { provideTextShareManager(get()) }
    factory<ClipboardCopyManager> { provideClipboardUpdateManager(get()) }
    single<VersionInfo> { VersionInfo(get()) }
    single<AppLauncher> { AppLauncher(get()) }
    single<AppRater> { AppRater(get()) }
}

private val featureApiModule = module {
    single(qualifier = FEATURE_API_MODULES) {
        listOf(
            HistoryFeature,
            TranslatorFeature,
            SettingsFeature,
            CameraFeature,
        )
    }
    single<ApplicationViewModel> { ApplicationViewModel(get()) }
    single<LanguageFeatureApi> { LanguageFeature }
}