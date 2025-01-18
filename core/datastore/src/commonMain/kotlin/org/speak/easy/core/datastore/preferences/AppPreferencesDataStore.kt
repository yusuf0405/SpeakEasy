package org.speak.easy.core.datastore.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.speak.easy.core.datastore.models.AppPreferencesDto

interface AppPreferencesDataStore {
    suspend fun setPreferences(preferences: AppPreferencesDto)
    fun observePreferences(): Flow<AppPreferencesDto>
    suspend fun getCurrentPreferences(): AppPreferencesDto
}

class DefaultAppPreferencesDataStore(
    private val produceFilePath: () -> String
) : AppPreferencesDataStore {

    private val dataStore: DataStore<AppPreferencesDto> = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = AppPreferencesJsonSerializer,
            producePath = { produceFilePath().toPath() }
        )
    )

    override suspend fun setPreferences(preferences: AppPreferencesDto) {
        dataStore.updateData { preferences }
    }

    override fun observePreferences(): Flow<AppPreferencesDto> {
        return dataStore.data
    }

    override suspend fun getCurrentPreferences(): AppPreferencesDto {
        return dataStore.data.first()
    }
}