package org.speak.easy.translator

import org.speak.easy.translator.models.LanguageUi

internal sealed interface TranslatorScreenAction {
    data object OnSwapLanguages : TranslatorScreenAction
    data object OnClearText : TranslatorScreenAction
    data object OnTranslateClick : TranslatorScreenAction
    data object OnClearSearchQuery : TranslatorScreenAction
    data object OnSpeakClick : TranslatorScreenAction
    data object OnShare : TranslatorScreenAction
    data class OnVoiceClick(val text: String, val languageCode: String) : TranslatorScreenAction
    data class OnSourceTextChange(val text: String) : TranslatorScreenAction
    data class OnSourceTextClick(val language: LanguageUi) : TranslatorScreenAction
    data class OnTargetTextClick(val language: LanguageUi) : TranslatorScreenAction
    data class OnSearch(val query: String) : TranslatorScreenAction
    data class OnCopy(val text: String) : TranslatorScreenAction
}