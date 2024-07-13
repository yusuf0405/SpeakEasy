package org.speak.easy.translator

import org.speak.easy.ui.components.models.LanguageUi

internal sealed interface TranslatorScreenAction {
    data object OnSwapLanguages : TranslatorScreenAction
    data object OnClearText : TranslatorScreenAction
    data object OnTranslateClick : TranslatorScreenAction
    data object OnSpeakClick : TranslatorScreenAction
    data object OnShare : TranslatorScreenAction
    data class OnVoiceClick(val text: String, val languageCode: String) : TranslatorScreenAction
    data class OnSourceTextChange(val text: String) : TranslatorScreenAction
    data class OnSourceTextClick(val language: LanguageUi) : TranslatorScreenAction
    data class OnTargetTextClick(val language: LanguageUi) : TranslatorScreenAction
    data class OnCopy(val text: String) : TranslatorScreenAction
}