package org.speak.easy.core.datastore

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use
import org.speak.easy.core.datastore.models.SelectedLanguageDto

internal object CurrentLanguageDataJsonSerializer : OkioSerializer<SelectedLanguageDto> {
    private val json = Json { ignoreUnknownKeys = true }
    override val defaultValue: SelectedLanguageDto = SelectedLanguageDto.unknown

    override suspend fun readFrom(source: BufferedSource): SelectedLanguageDto {
        return json.decodeFromString<SelectedLanguageDto>(source.readUtf8())
    }

    override suspend fun writeTo(t: SelectedLanguageDto, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(SelectedLanguageDto.serializer(), t))
        }
    }
}