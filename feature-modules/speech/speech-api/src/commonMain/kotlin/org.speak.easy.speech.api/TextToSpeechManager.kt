package org.speak.easy.speech.api

interface TextToSpeechManager {
    fun speak(text: String, languageCode: String)
}