package org.speak.easy.speech

import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.speech.api.TextToSpeechManager

expect fun provideTextToSpeech(
    platformConfiguration: PlatformConfiguration
): TextToSpeechManager