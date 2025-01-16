package org.speak.easy.data.repositories

import org.speak.easy.core.network.source.TranslatorCloudDataSource
import org.speak.easy.data.mappers.LanguageCloudToDataMapper
import org.speak.easy.data.mappers.LanguageDataToDomainMapper
import org.speak.easy.domain.models.LanguageDomain
import org.speak.easy.domain.models.LanguageType

internal interface LanguageService {
    suspend fun fetchLanguages(type: LanguageType): List<LanguageDomain>
}

internal class DefaultLanguageService(
    private val cloudDataSource: TranslatorCloudDataSource,
    private val cloudToDataMapper: LanguageCloudToDataMapper,
    private val dataToDomainMapper: LanguageDataToDomainMapper
) : LanguageService {

    override suspend fun fetchLanguages(type: LanguageType): List<LanguageDomain> {
        return cloudDataSource.fetchLanguages(type.name)
            .map(cloudToDataMapper::map)
            .map(dataToDomainMapper::map)
    }
}