package org.speak.easy.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.speak.easy.core.datastore.CurrentLanguageDataStore
import org.speak.easy.data.mappers.SelectedLanguageDataToDomainMapper
import org.speak.easy.data.mappers.SelectedLanguageDataToDtoMapper
import org.speak.easy.data.mappers.SelectedLanguageDomainToDataMapper
import org.speak.easy.data.mappers.SelectedLanguageDtoToDataMapper
import org.speak.easy.domain.models.SelectedLanguageDomain

internal interface CurrentLanguageManager {
    suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain)
    suspend fun fetchLatestSelectedLanguage(): SelectedLanguageDomain
    fun observeSelectedLanguageData(): Flow<SelectedLanguageDomain>
}

internal class DefaultCurrentLanguageManager(
    private val languageDataStore: CurrentLanguageDataStore,
    private val dataToDomainMapper: SelectedLanguageDataToDomainMapper,
    private val domainToDataMapper: SelectedLanguageDomainToDataMapper,
    private val dtoToDataMapper: SelectedLanguageDtoToDataMapper,
    private val dataToDtoMapper: SelectedLanguageDataToDtoMapper
) : CurrentLanguageManager {

    override suspend fun updateSelectedLanguage(selectedLanguage: SelectedLanguageDomain) {
        val data = domainToDataMapper.map(selectedLanguage)
        languageDataStore.updateSelectedLanguage(
            dataToDtoMapper.map(data)
        )
    }

    override suspend fun fetchLatestSelectedLanguage(): SelectedLanguageDomain {
        return languageDataStore
            .getSelectedLanguageData()
            .run(dtoToDataMapper::map)
            .run(dataToDomainMapper::map)
    }

    override fun observeSelectedLanguageData(): Flow<SelectedLanguageDomain> {
        return languageDataStore
            .observeSelectedLanguageData()
            .map(dtoToDataMapper::map)
            .map(dataToDomainMapper::map)
    }
}