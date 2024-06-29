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
import platform.Speech.SFSpeechAudioBufferRecognitionRequest
import platform.Speech.SFSpeechRecognitionTask
import platform.Speech.SFSpeechRecognizer
import platform.Speech.SFSpeechRecognizerAuthorizationStatus

private class IosSpeechRecognizerManager : SpeechRecognizerManager {

    private var audioEngine: AVAudioEngine? = null
    private var recognitionTask: SFSpeechRecognitionTask? = null
    private var recognitionRequest: SFSpeechAudioBufferRecognitionRequest? = null
    private val speechRecognizer = SFSpeechRecognizer()

    private val speechResultFlow: MutableStateFlow<SpeechRecognizerResult> =
        MutableStateFlow(SpeechRecognizerResult.StopListening)

    override fun observeSpeech(): Flow<SpeechRecognizerResult> = speechResultFlow.asStateFlow()

    @OptIn(ExperimentalForeignApi::class)
    override fun startListening(languageCode: String) {
        try {
            stopListening()
            speechResultFlow.tryEmit(SpeechRecognizerResult.StartListening)
            audioEngine = AVAudioEngine()
            SFSpeechRecognizer.requestAuthorization { status ->
                if (status != SFSpeechRecognizerAuthorizationStatus.SFSpeechRecognizerAuthorizationStatusAuthorized) {
                    speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
                    return@requestAuthorization
                }

                recognitionRequest = SFSpeechAudioBufferRecognitionRequest()
                val inputNode = audioEngine?.inputNode
                val audioSession = AVAudioSession.sharedInstance()

                audioSession.setCategory(
                    category = AVAudioSessionCategoryRecord,
                    mode = AVAudioSessionModeMeasurement,
                    options = AVAudioSessionCategoryOptionDuckOthers,
                    error = null
                )
                audioSession.setActive(
                    active = true,
                    withOptions = AVAudioSessionSetActiveOptionNotifyOthersOnDeactivation,
                    error = null
                )

                recognitionTask =
                    speechRecognizer.recognitionTaskWithRequest(recognitionRequest!!) { result, error ->
                        if (result != null) {
                            speechResultFlow.tryEmit(SpeechRecognizerResult.Success(result.bestTranscription.formattedString))
                            stopListening()
                        } else if (error != null) {
                            speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
                            stopListening()
                        }
                    }

                val recordingFormat = inputNode?.inputFormatForBus(0u)
                inputNode?.installTapOnBus(0u, bufferSize = 1024u, recordingFormat) { buffer, _ ->
                    if (buffer != null) {
                        recognitionRequest?.appendAudioPCMBuffer(buffer)
                    }
                }
                audioEngine?.prepare()
                audioEngine?.startAndReturnError(null)

            }
        } catch (e: Throwable) {
            stopListening()
            speechResultFlow.tryEmit(SpeechRecognizerResult.Error)
        }
    }

    override fun stopListening() {
        audioEngine?.stop()
        recognitionRequest?.endAudio()
        recognitionTask?.cancel()
        audioEngine?.stop()
        audioEngine?.inputNode?.removeTapOnBus(0u)
        speechResultFlow.tryEmit(SpeechRecognizerResult.StopListening)
    }
}

actual fun provideSpeechRecognizerManager(platformConfiguration: PlatformConfiguration): SpeechRecognizerManager =
    IosSpeechRecognizerManager()
