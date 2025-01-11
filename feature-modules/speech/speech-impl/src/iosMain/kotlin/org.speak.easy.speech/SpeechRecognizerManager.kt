package org.speak.easy.speech

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.SpeechRecognizerManager
import org.speak.easy.speech.api.SpeechRecognizerResult
import platform.AVFAudio.AVAudioEngine
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryOptionDuckOthers
import platform.AVFAudio.AVAudioSessionCategoryRecord
import platform.AVFAudio.AVAudioSessionModeMeasurement
import platform.AVFAudio.AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation
import platform.AVFAudio.setActive
import platform.Foundation.NSThread
import platform.Speech.SFSpeechAudioBufferRecognitionRequest
import platform.Speech.SFSpeechRecognitionTask
import platform.Speech.SFSpeechRecognizer
import platform.Speech.SFSpeechRecognizerAuthorizationStatus

class IosSpeechRecognizerManager : SpeechRecognizerManager {

    private var audioEngine: AVAudioEngine? = null
    private var recognitionTask: SFSpeechRecognitionTask? = null
    private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest? = null
    private val speechRecognizer = SFSpeechRecognizer()

    private val speechResultFlow: MutableStateFlow<SpeechRecognizerResult> =
        MutableStateFlow(SpeechRecognizerResult.StopListening)

    override fun observeSpeech(): Flow<SpeechRecognizerResult> = speechResultFlow.asStateFlow()

    override fun startListening(languageCode: String) {
        if (!NSThread.isMainThread) return
        try {
            resetResources()

            val permissionStatus = SFSpeechRecognizer.authorizationStatus()
            if (permissionStatus != SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized) {
                speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
                return
            }

            speechResultFlow.tryEmit(SpeechRecognizerResult.StartListening)

            audioEngine = AVAudioEngine()
            recognitionRequest = SFSpeechAudioBufferRecognitionRequest()

            setupAudioSession()
            setupRecognitionTask()
            startAudioEngine()
        } catch (e: Throwable) {
            handleError(e.stackTraceToString())
        }
    }

    override fun stopListening() {
        resetResources()
        speechResultFlow.tryEmit(SpeechRecognizerResult.StopListening)
    }

    private fun resetResources() {
        recognitionTask?.cancel()
        recognitionRequest?.endAudio()
        audioEngine?.stop()
        audioEngine?.inputNode?.removeTapOnBus(0u)

        recognitionTask = null
        recognitionRequest = null
        audioEngine = null
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupAudioSession() {
        val audioSession = AVAudioSession.sharedInstance()
        audioSession.setCategory(
            category = AVAudioSessionCategoryRecord,
            mode = AVAudioSessionModeMeasurement,
            options = AVAudioSessionCategoryOptionDuckOthers,
            error = null
        )
        audioSession.setActive(
            true,
            withOptions = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation,
            error = null
        )
    }

    private fun setupRecognitionTask() {
        recognitionTask =
            speechRecognizer.recognitionTaskWithRequest(recognitionRequest!!) { result, error ->
                if (error != null) {
                    handleError(error.localizedDescription)
                    return@recognitionTaskWithRequest
                }
                result?.let {
                    speechResultFlow.tryEmit(SpeechRecognizerResult.Success(it.bestTranscription.formattedString))
                }
            }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun startAudioEngine() {
        val inputNode = audioEngine?.inputNode
        val recordingFormat = inputNode?.inputFormatForBus(0u)
        inputNode?.installTapOnBus(0u, bufferSize = 1024u, format = recordingFormat) { buffer, _ ->
            buffer?.let { recognitionRequest?.appendAudioPCMBuffer(it) }
        }
        audioEngine?.prepare()
        audioEngine?.startAndReturnError(null)
    }

    private fun handleError(errorMessage: String) {
        resetResources()
        println(errorMessage)
        speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
    }
}

actual fun provideSpeechRecognizerManager(platformConfiguration: PlatformConfiguration): SpeechRecognizerManager =
    IosSpeechRecognizerManager()
