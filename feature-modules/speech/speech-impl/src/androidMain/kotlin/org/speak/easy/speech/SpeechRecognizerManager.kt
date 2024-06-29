package org.speak.easy.speech

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.SpeechRecognizerManager
import org.speak.easy.speech.api.SpeechRecognizerResult
import java.util.Locale

private class AndroidSpeechRecognizerManager(
    private val platformConfiguration: PlatformConfiguration
) : SpeechRecognizerManager {

    private val speechResultFlow =
        MutableStateFlow<SpeechRecognizerResult>(SpeechRecognizerResult.StopListening)

    private val speechRecognizer by lazy {
        SpeechRecognizer.createSpeechRecognizer(platformConfiguration.androidContext)
    }

    private var resultListener: RecognitionListener = object : RecognitionListener {

        override fun onReadyForSpeech(params: Bundle?) {
            speechResultFlow.tryEmit(SpeechRecognizerResult.StartListening)
            Log.i("SpeechRecognizer", "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            Log.i("SpeechRecognizer", "onBeginningOfSpeech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            Log.i("SpeechRecognizer", "onRmsChanged")
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            Log.i("SpeechRecognizer", "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            speechResultFlow.tryEmit(SpeechRecognizerResult.StopListening)
            Log.i("SpeechRecognizer", "onEndOfSpeech")
        }

        override fun onError(error: Int) {
            if (speechResultFlow.value == SpeechRecognizerResult.StopListening) return
            speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
            Log.i("SpeechRecognizer", "onError: ${error}")
        }

        override fun onResults(results: Bundle?) {
            val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
            val text = matches?.joinToString(separator = " ").orEmpty()
            speechResultFlow.tryEmit(SpeechRecognizerResult.Success(text))
        }

        override fun onPartialResults(partialResults: Bundle?) {
            Log.i("SpeechRecognizer", "onPartialResults")
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            Log.i("SpeechRecognizer", "onEvent")
        }
    }

    init {
        speechRecognizer.setRecognitionListener(resultListener)
    }

    override fun observeSpeech(): Flow<SpeechRecognizerResult> {
        return speechResultFlow
    }

    override fun startListening(languageCode: String) {
        val language = Locale(languageCode.lowercase())
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        }
        speechRecognizer.startListening(speechRecognizerIntent)
    }

    override fun stopListening() {
        speechRecognizer.stopListening()
        speechResultFlow.tryEmit(SpeechRecognizerResult.StopListening)
    }
}

actual fun provideSpeechRecognizerManager(platformConfiguration: PlatformConfiguration): SpeechRecognizerManager =
    AndroidSpeechRecognizerManager(platformConfiguration)