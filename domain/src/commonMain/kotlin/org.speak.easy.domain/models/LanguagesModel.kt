package org.speak.easy.domain.models

data class LanguagesModel(
    val sourceLanguages: List<LanguageDomain>,
    val targetLanguages: List<LanguageDomain>
)