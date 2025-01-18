package org.speak.easy.settings.di

import org.koin.dsl.module
import org.speak.easy.settings.SettingsViewModel
import org.speak.easy.settings.about.app.AboutAppInfoFactory
import org.speak.easy.settings.about.app.AboutAppViewModel
import org.speak.easy.settings.about.app.DefaultAboutAppInfoFactory
import org.speak.easy.settings.category.CategoryFactory
import org.speak.easy.settings.theme.ThemeViewModel

val settingsModule = module {
    single { SettingsViewModel(get(), get(), get(), get()) }
    single { ThemeViewModel(get()) }
    single { AboutAppViewModel(get(), get(), get()) }
    factory { CategoryFactory() }
    factory<AboutAppInfoFactory> { DefaultAboutAppInfoFactory(get()) }
}