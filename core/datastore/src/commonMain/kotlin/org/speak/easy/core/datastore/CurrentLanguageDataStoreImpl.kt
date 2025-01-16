package org.speak.easy.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.speak.easy.core.datastore.models.SelectedLanguageDto

class CurrentLanguageDataStoreImpl(
    private val produceFilePath: () -> String
) : CurrentLanguageDataStore {

    private val dataStore: DataStore<SelectedLanguageDto> = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = CurrentLanguageDataJsonSerializer,
            producePath = { produceFilePath().toPath() }
        )
    )

    override suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDto) {
        dataStore.updateData { selectedLanguage }
    }

    override suspend fun getSelectedLanguageData(): SelectedLanguageDto {
        return dataStore.data.first()
    }

    override fun observeSelectedLanguageData(): Flow<SelectedLanguageDto> {
        return dataStore.data
    }
}