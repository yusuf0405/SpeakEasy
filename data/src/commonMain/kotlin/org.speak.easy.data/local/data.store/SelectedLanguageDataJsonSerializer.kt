package org.speak.easy.data.local.data.store

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use
import org.speak.easy.data.models.SelectedLanguageData

internal object SelectedLanguageDataJsonSerializer : OkioSerializer<SelectedLanguageData> {
    private val json = Json { ignoreUnknownKeys = true }
    override val defaultValue: SelectedLanguageData = SelectedLanguageData.unknown

    override suspend fun readFrom(source: BufferedSource): SelectedLanguageData {
        return json.decodeFromString<SelectedLanguageData>(source.readUtf8())
    }

    override suspend fun writeTo(t: SelectedLanguageData, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(SelectedLanguageData.serializer(), t))
        }
    }
}