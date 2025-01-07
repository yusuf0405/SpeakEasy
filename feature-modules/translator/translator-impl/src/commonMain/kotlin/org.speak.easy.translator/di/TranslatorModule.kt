package org.speak.easy.translator.di

import org.koin.dsl.module
import org.speak.easy.translator.SourceTextManagerImpl
import org.speak.easy.translator.TranslatorViewModel
import org.speak.easy.translator.api.SourceTextManager

val translatorModule = module {
    single {
        TranslatorViewModel(
            get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    single<SourceTextManager> { SourceTextManagerImpl() }
}