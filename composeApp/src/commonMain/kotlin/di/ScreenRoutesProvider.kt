package di

import org.speak.easy.history.HistoryFeatureImpl
import org.speak.easy.translator.TranslatorFeatureImpl

object ScreenRoutesProvider {

    fun getTranslatorRoute(): String = TranslatorFeatureImpl.provideTranslatorRoute()

    fun getHistoryRoute(): String = HistoryFeatureImpl.provideHistoryRoute()
}