package org.speak.easy.translator

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import org.speak.easy.translator.api.SourceTextManager

class SourceTextManagerImpl : SourceTextManager {

    private val mutableSourceText = MutableStateFlow("")
    override val sourceText: Flow<String> = mutableSourceText

    override fun setText(text: String) {
        mutableSourceText.tryEmit(text)
    }
}