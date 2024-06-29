package org.speak.easy.speech.api

import kotlinx.coroutines.flow.Flow
import org.speak.easy.core.PlatformConfiguration

sealed interface SpeechRecognizerResult {
    data object StartListening : SpeechRecognizerResult
    data object StopListening : SpeechRecognizerResult
    data class Success(val text: String) : SpeechRecognizerResult
    data object Error : SpeechRecognizerResult
}

interface SpeechRecognizerManager {
    fun observeSpeech(): Flow<SpeechRecognizerResult>
    fun startListening(languageCode: String)
    fun stopListening()
}