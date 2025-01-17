package org.speak.easy.core.datastore.preferences

import androidx.datastore.core.okio.OkioSerializer
import kotlinx.serialization.json.Json
import okio.BufferedSink
import okio.BufferedSource
import okio.use
import org.speak.easy.core.datastore.models.AppPreferencesDto

internal object AppPreferencesJsonSerializer : OkioSerializer<AppPreferencesDto> {
    private val json = Json { ignoreUnknownKeys = true }
    override val defaultValue: AppPreferencesDto = AppPreferencesDto.default

    override suspend fun readFrom(source: BufferedSource): AppPreferencesDto {
        return json.decodeFromString<AppPreferencesDto>(source.readUtf8())
    }

    override suspend fun writeTo(t: AppPreferencesDto, sink: BufferedSink) {
        sink.use {
            it.writeUtf8(json.encodeToString(AppPreferencesDto.serializer(), t))
        }
    }
}