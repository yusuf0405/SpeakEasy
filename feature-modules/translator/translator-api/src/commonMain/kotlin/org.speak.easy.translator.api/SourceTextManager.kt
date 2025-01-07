package org.speak.easy.translator.api

import kotlinx.coroutines.flow.Flow

interface SourceTextManager {
    val sourceText: Flow<String>
    fun setText(text: String)
}