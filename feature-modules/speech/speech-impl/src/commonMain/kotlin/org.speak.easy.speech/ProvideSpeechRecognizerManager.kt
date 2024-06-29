package org.speak.easy.speech

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.SpeechRecognizerManager

expect fun provideSpeechRecognizerManager(
    platformConfiguration: PlatformConfiguration
): SpeechRecognizerManager