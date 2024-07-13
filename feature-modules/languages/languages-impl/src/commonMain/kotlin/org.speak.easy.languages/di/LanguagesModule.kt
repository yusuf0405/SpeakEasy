package org.speak.easy.languages.di

import org.koin.dsl.module
import org.speak.easy.languages.LanguagesViewModel

val languagesModule = module {
    single { LanguagesViewModel(get(), get(), get()) }
}