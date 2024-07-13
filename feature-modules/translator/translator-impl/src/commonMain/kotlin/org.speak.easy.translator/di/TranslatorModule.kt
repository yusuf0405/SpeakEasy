package org.speak.easy.translator.di

import org.koin.dsl.module
import org.speak.easy.translator.TranslatorViewModel

val translatorModule = module {
    single { TranslatorViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
}