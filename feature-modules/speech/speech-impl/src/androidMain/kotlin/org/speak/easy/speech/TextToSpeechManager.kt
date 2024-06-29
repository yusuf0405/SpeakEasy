package org.speak.easy.speech

import android.media.AudioAttributes
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.TextToSpeechManager
import java.util.Locale
import kotlin.math.min

private class AndroidTextToSpeechManager(
    private val platformConfiguration: PlatformConfiguration
) : TextToSpeechManager, TextToSpeech.OnInitListener, UtteranceProgressListener() {

    private var isInit = false
    private var wordsToSpeak: List<String> = ArrayList()
    private var currentSpokenWordIndex: Int = 0
    private var previousSpokenWordIndex: Int = 0

    // This is the max number of words to speak per chunk, this is for pause/resume speaking
    private val kNumWordsToSpeakPerChunk = 25

    private val textToSpeech: TextToSpeech by lazy {
        TextToSpeech(platformConfiguration.androidContext, this)
    }

    init {
        textToSpeech.setOnUtteranceProgressListener(this)
        textToSpeech.setSpeechRate(1f)
        textToSpeech.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_ACCESSIBILITY)
                .build()
        )
    }

    override fun onInit(status: Int) {
        if (status === TextToSpeech.SUCCESS) {
            val locale = Locale.US
            val result = textToSpeech.setLanguage(locale)
            isInit =
                !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
        } else {
            isInit = false
        }
    }

    override fun speak(text: String, languageCode: String) {
        // Resume speaking if there is unspoken text
        if (!isSpeaking() && wordsToSpeak.isNotEmpty() && currentSpokenWordIndex < wordsToSpeak.size) {
            resumeSpeaking()
            return
        }

        stopSpeaking()
        setLanguage(Locale(languageCode.lowercase()))
        wordsToSpeak = text.split(" ")
        currentSpokenWordIndex = 0

        speakNextWords()
    }

    private fun speakNextWords() {
        previousSpokenWordIndex =
            currentSpokenWordIndex // Save this in case we need to resume speaking
        val nextWordsToSpeak = wordsToSpeak.subList(
            currentSpokenWordIndex,
            currentSpokenWordIndex + min(
                kNumWordsToSpeakPerChunk,
                wordsToSpeak.size - currentSpokenWordIndex
            )
        )
        currentSpokenWordIndex += nextWordsToSpeak.size

        val map = Bundle()
        map.putString(
            TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
            "freds_markers$currentSpokenWordIndex"
        )
        map.putString(TextToSpeech.Engine.KEY_PARAM_STREAM, "Map Marker Description Audio")

        textToSpeech.speak(
            nextWordsToSpeak.joinToString(" "),
            TextToSpeech.QUEUE_ADD,
            map,
            "freds_markers"
        )
    }

    fun pauseSpeaking() {
        textToSpeech.stop()
        currentSpokenWordIndex = maxOf(previousSpokenWordIndex - kNumWordsToSpeakPerChunk, 0)
    }

    fun resumeSpeaking() {
        // Speak the last word to resume speaking
        speakNextWords()
    }

    fun stopSpeaking() {
        textToSpeech.stop()
        wordsToSpeak = ArrayList()
        currentSpokenWordIndex = 0
    }

    fun isSpeaking(): Boolean {
        return textToSpeech.isSpeaking
    }

    override fun onStart(utteranceId: String?) {
        Log.d("tts", "onStart: $utteranceId")
        // Yes, this queues up the next words to speak RIGHT AFTER the current word has started speaking.
        // The issue is that the time between words is too long, so we need to queue up as fast as possible.
        if (wordsToSpeak.isNotEmpty() && currentSpokenWordIndex < wordsToSpeak.size) {
            speakNextWords()
        }
    }

    override fun onDone(utteranceId: String?) {
        Log.d("tts", "onDone: $utteranceId")
    }

    @Suppress("DeprecatedCallableAddReplaceWith")
    @Deprecated("deprecated")
    override fun onError(utteranceId: String?) {
        Log.d("tts", "onError: $utteranceId")
    }

    fun setLanguage(locale: Locale): Int {
        return textToSpeech.setLanguage(locale)
    }

    fun shutdown() {
        textToSpeech.shutdown()
    }
}

actual fun provideTextToSpeech(
    platformConfiguration: PlatformConfiguration
): TextToSpeechManager = AndroidTextToSpeechManager(platformConfiguration)