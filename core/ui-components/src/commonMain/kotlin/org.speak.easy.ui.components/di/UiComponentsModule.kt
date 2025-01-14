package org.speak.easy.ui.components.di

import org.koin.dsl.module
import org.speak.easy.ui.components.mappers.LanguageDomainToUiMapper

val uiComponentsModule = module {
    factory { LanguageDomainToUiMapper() }
}