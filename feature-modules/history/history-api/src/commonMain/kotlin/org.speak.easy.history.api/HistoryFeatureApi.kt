package org.speak.easy.history.api

import org.speak.easy.core.FeatureApi

interface HistoryFeatureApi : FeatureApi {

    fun provideHistoryRoute(): String
}