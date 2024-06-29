package org.speak.easy.history.di

import org.koin.dsl.module
import org.speak.easy.history.HistoryViewModel
import org.speak.easy.history.mappers.TranslationHistoryDomainToUiMapper

val historyModule = module {
    factory { HistoryViewModel(get(), get()) }
    factory { TranslationHistoryDomainToUiMapper() }
}