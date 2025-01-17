package org.speak.easy.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.datastore.preferences.AppPreferencesDataStore
import org.speak.easy.data.mappers.AppPreferencesDataDomainToMapper
import org.speak.easy.data.mappers.AppPreferencesDataToDtoMapper
import org.speak.easy.data.mappers.AppPreferencesDtoToDataMapper
import org.speak.easy.data.mappers.AppPreferencesToDataMapper
import org.speak.easy.domain.PreferencesRepository
import org.speak.easy.domain.models.AppPreferences

class PreferencesRepositoryImpl(
    private val dispatcherProvider: DispatcherProvider,
    private val preferencesDataStore: AppPreferencesDataStore,
    private val preferencesDataToDtoMapper: AppPreferencesDataToDtoMapper,
    private val preferencesDtoToDataMapper: AppPreferencesDtoToDataMapper,
    private val preferencesToDataMapper: AppPreferencesToDataMapper,
    private val preferencesDataDomainToMapper: AppPreferencesDataDomainToMapper,
) : PreferencesRepository {

    override suspend fun setPreferences(preferences: AppPreferences) {
        return withContext(dispatcherProvider.io) {
            val dataPreferences = preferencesToDataMapper.map(preferences)
            preferencesDataStore.setPreferences(preferencesDataToDtoMapper.map(dataPreferences))
        }
    }

    override fun observePreferences(): Flow<AppPreferences> {
        return preferencesDataStore
            .observePreferences()
            .map(preferencesDtoToDataMapper::map)
            .map(preferencesDataDomainToMapper::map)
            .flowOn(dispatcherProvider.io)
    }

    override suspend fun getCurrentPreferences(): AppPreferences {
        return withContext(dispatcherProvider.io) {
            preferencesDataStore.getCurrentPreferences()
                .run(preferencesDtoToDataMapper::map)
                .run(preferencesDataDomainToMapper::map)
        }
    }
}