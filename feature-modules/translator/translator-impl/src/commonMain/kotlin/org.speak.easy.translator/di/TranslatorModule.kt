package org.speak.easy.translator.di

import org.koin.dsl.module
import org.speak.easy.core.ClipboardCopyManager
import org.speak.easy.translator.TranslatorViewModel
import org.speak.easy.translator.mappers.LanguageDomainToUiMapper
import org.speak.easy.core.provideClipboardUpdateManager

val translatorModule = module {
    single { TranslatorViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { LanguageDomainToUiMapper() }
}