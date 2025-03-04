package org.speak.easy.speech

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.TextToSpeechManager
import platform.AVFAudio.AVSpeechBoundary
import platform.AVFAudio.AVSpeechSynthesisVoice
import platform.AVFAudio.AVSpeechSynthesizer
import platform.AVFAudio.AVSpeechSynthesizerDelegateProtocol
import platform.AVFAudio.AVSpeechUtterance
import platform.NaturalLanguage.NLLanguageRecognizer
import platform.darwin.NSObject

private class IosTextToSpeechManager : TextToSpeechManager {

    override fun speak(text: String, languageCode: String) {
        speak(text)
        val synthesizer = AVSpeechSynthesizer()
        val utterance = AVSpeechUtterance(string = text)
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(languageCode)
        utterance.setRate(0.4f)
        synthesizer.speakUtterance(utterance)
    }

    private var synthesizer: AVSpeechSynthesizer = AVSpeechSynthesizer()
    var isSpeaking = false
    var isPaused = false
    var utterance = AVSpeechUtterance()

    fun speak(text: String) {
        if (!isSpeaking && isPaused) {
            continueSpeaking()
        }

        utterance = AVSpeechUtterance.speechUtteranceWithString(text)
        utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage("en-US")
        if (utterance.voice == null) {
            println("Voice not found for language: ${utterance.voice?.language}")
            return
        }

        val recognizer = NLLanguageRecognizer()
        recognizer.processString(text)
        val language = recognizer.dominantLanguage
        language ?: run {
            utterance.voice = AVSpeechSynthesisVoice.voiceWithLanguage(language)
        }

        isPaused = false
        isSpeaking = true
        synthesizer.speakUtterance(utterance)
    }

    fun speechSynthesizer(
        synthesizer: AVSpeechSynthesizer,
        @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
        didFinishSpeechUtterance: AVSpeechUtterance
    ) {
        isSpeaking = false
    }

    fun stopSpeaking() {
        synthesizer.stopSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        isSpeaking = false
        isPaused = false
    }

    fun pauseSpeaking() {
        synthesizer.pauseSpeakingAtBoundary(AVSpeechBoundary.AVSpeechBoundaryImmediate)
        isPaused = true
        isSpeaking = false
    }

    fun continueSpeaking() {
        synthesizer.continueSpeaking()
        isPaused = false
        isSpeaking = true
    }

    fun isSpeaking(): Boolean {
        return isSpeaking
    }
}

actual fun provideTextToSpeech(
    platformConfiguration: PlatformConfiguration
): TextToSpeechManager = IosTextToSpeechManager()
