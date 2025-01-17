package org.speak.easy.domain.di

import org.koin.dsl.module
import org.speak.easy.domain.holders.LanguagesHolder
import org.speak.easy.domain.holders.LanguagesHolderImpl
import org.speak.easy.domain.usecases.DefaultThemeUseCase
import org.speak.easy.domain.usecases.ThemeUseCase

val domainModule = module {
    single<LanguagesHolder> { LanguagesHolderImpl(get()) }
    factory<ThemeUseCase> { DefaultThemeUseCase(get()) }
}