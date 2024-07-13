package org.speak.easy.settings.api

import org.speak.easy.core.FeatureApi

interface SettingsFeatureApi : FeatureApi {

    fun provideScreenRoute(): String
}