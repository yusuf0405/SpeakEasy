package org.speak.easy.data.local.data.store

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import org.speak.easy.data.models.SelectedLanguageData

class SelectedLanguageDataStoreImpl(
    private val produceFilePath: () -> String
) : SelectedLanguageDataStore {

    private val dataStore: DataStore<SelectedLanguageData> = DataStoreFactory.create(
        storage = OkioStorage(
            fileSystem = FileSystem.SYSTEM,
            serializer = SelectedLanguageDataJsonSerializer,
            producePath = { produceFilePath().toPath() }
        )
    )

    override suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageData) {
        dataStore.updateData { selectedLanguage }
    }

    override suspend fun getSelectedLanguageData(): SelectedLanguageData {
        return dataStore.data.first()
    }

    override fun observeSelectedLanguageData(): Flow<SelectedLanguageData> {
        return dataStore.data
    }
}