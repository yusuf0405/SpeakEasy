package org.speak.easy.settings.di

import org.koin.dsl.module
import org.speak.easy.settings.SettingsViewModel
import org.speak.easy.settings.category.CategoryFactory
import org.speak.easy.settings.theme.ThemeViewModel

val settingsModule = module {
    single { SettingsViewModel(get(), get(), get(), get()) }
    single { ThemeViewModel(get()) }
    factory { CategoryFactory() }
}