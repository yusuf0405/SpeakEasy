package org.speak.easy.domain.di

import org.koin.dsl.module
import org.speak.easy.domain.holders.LanguagesHolder
import org.speak.easy.domain.holders.LanguagesHolderImpl

val domainModule = module {
    factory<LanguagesHolder> { LanguagesHolderImpl(get()) }
}