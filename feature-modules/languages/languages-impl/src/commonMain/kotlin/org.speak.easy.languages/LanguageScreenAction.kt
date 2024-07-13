package org.speak.easy.languages

internal sealed interface LanguageScreenAction {
    data object OnClearSearchQuery : LanguageScreenAction
    data class OnSearch(val query: String) : LanguageScreenAction
}