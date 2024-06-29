package org.speak.easy.speech

import org.koin.dsl.module
import org.speak.easy.speech.api.SpeechRecognizerManager
import org.speak.easy.speech.api.TextToSpeechManager

val speechFeatureModule = module {
    single<TextToSpeechManager> { provideTextToSpeech(get()) }
    single<SpeechRecognizerManager> { provideSpeechRecognizerManager(get()) }
}