package org.speak.easy.translator.api

import org.speak.easy.core.FeatureApi

interface TranslatorFeatureApi : FeatureApi {

    fun provideScreenRoute(): String
}