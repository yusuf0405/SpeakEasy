package org.speak.easy.data.di

import org.koin.core.module.Module
import org.koin.dsl.module
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.database.DatabaseFactory
import org.speak.easy.core.database.LanguageHistoryDao
import org.speak.easy.core.database.TranslationHistoryDao
import org.speak.easy.core.database.TranslationHistoryDatabase
import org.speak.easy.core.database.source.LanguageHistoryLocalDataSource
import org.speak.easy.core.database.source.LanguageHistoryLocalDataSourceImpl
import org.speak.easy.core.database.source.TranslationHistoryLocalDataSource
import org.speak.easy.core.database.source.TranslationHistoryLocalDataSourceImpl
import org.speak.easy.core.datastore.DatastoreFactory
import org.speak.easy.core.datastore.CurrentLanguageDataStore
import org.speak.easy.core.datastore.preferences.AppPreferencesDataStore
import org.speak.easy.core.network.di.networkModule
import org.speak.easy.core.network.service.DeeplCloudService
import org.speak.easy.core.network.service.DeeplCloudServiceImpl
import org.speak.easy.core.network.source.TranslatorCloudDataSource
import org.speak.easy.core.network.source.TranslatorCloudDataSourceImpl
import org.speak.easy.core.provideDispatcher
import org.speak.easy.data.mappers.AppPreferencesDataDomainToMapper
import org.speak.easy.data.mappers.AppPreferencesDataToDtoMapper
import org.speak.easy.data.mappers.AppPreferencesDtoToDataMapper
import org.speak.easy.data.mappers.AppPreferencesToDataMapper
import org.speak.easy.data.mappers.LanguageCloudToDataMapper
import org.speak.easy.data.mappers.LanguageDataToDomainMapper
import org.speak.easy.data.mappers.LanguageDataToEntityMapper
import org.speak.easy.data.mappers.LanguageDomainToDataMapper
import org.speak.easy.data.mappers.LanguageHistoryEntityToDataMapper
import org.speak.easy.data.mappers.SelectedLanguageDataToDomainMapper
import org.speak.easy.data.mappers.SelectedLanguageDataToDtoMapper
import org.speak.easy.data.mappers.SelectedLanguageDomainToDataMapper
import org.speak.easy.data.mappers.SelectedLanguageDtoToDataMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDataToCloudMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDomainToDataMapper
import org.speak.easy.data.mappers.TranslationCloudToDataMapper
import org.speak.easy.data.mappers.TranslationDataToDomainMapper
import org.speak.easy.data.mappers.TranslationHistoryDataToDomainMapper
import org.speak.easy.data.mappers.TranslationHistoryItemToDataMapper
import org.speak.easy.data.repositories.DefaultLanguageService
import org.speak.easy.data.repositories.DefaultCurrentLanguageManager
import org.speak.easy.data.repositories.DefaultTranslationHistoryManager
import org.speak.easy.data.repositories.DefaultTranslationService
import org.speak.easy.data.repositories.LanguageHistoryRepositoryImpl
import org.speak.easy.data.repositories.LanguageService
import org.speak.easy.data.repositories.CurrentLanguageManager
import org.speak.easy.data.repositories.PreferencesRepositoryImpl
import org.speak.easy.data.repositories.TranslationHistoryManager
import org.speak.easy.data.repositories.TranslationRepositoryImpl
import org.speak.easy.data.repositories.TranslationService
import org.speak.easy.domain.LanguageHistoryRepository
import org.speak.easy.domain.PreferencesRepository
import org.speak.easy.domain.TranslationRepository


fun getSharedModule(): List<Module> = listOf(
    networkModule,
    dataModule,
    mappersModule,
    databaseModule
)

private val dataModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
    factory<LanguageService> { DefaultLanguageService(get(), get(), get()) }
    factory<TranslationHistoryManager> { DefaultTranslationHistoryManager(get(), get(), get()) }
    factory<CurrentLanguageManager> {
        DefaultCurrentLanguageManager(
            get(), get(), get(), get(), get()
        )
    }
    factory<TranslationService> {
        DefaultTranslationService(
            get(), get(), get(), get(), get(), get()
        )
    }
    single<DeeplCloudService> { DeeplCloudServiceImpl(get()) }
    single<TranslatorCloudDataSource> { TranslatorCloudDataSourceImpl(get(), get()) }
    single<LanguageHistoryRepository> {
        LanguageHistoryRepositoryImpl(get(), get(), get(), get(), get())
    }
    single<TranslationRepository> {
        TranslationRepositoryImpl(get(), get(), get(), get())
    }
    single<PreferencesRepository> {
        PreferencesRepositoryImpl(get(), get(), get(), get(), get(), get())
    }
}

internal val databaseModule = module {
    single { DatabaseFactory(get()) }
    single { DatastoreFactory(get()) }
    single<TranslationHistoryDatabase> { get<DatabaseFactory>().createRoomDatabase() }
    single<CurrentLanguageDataStore> { get<DatastoreFactory>().createSelectedLanguageDataStore() }
    single<AppPreferencesDataStore> { get<DatastoreFactory>().createAppPreferencesDataStore() }
    single<TranslationHistoryDao> { get<TranslationHistoryDatabase>().getTranslationHistoryDao() }
    single<LanguageHistoryDao> { get<TranslationHistoryDatabase>().getLanguageHistoryDao() }
    single<TranslationHistoryLocalDataSource> {
        TranslationHistoryLocalDataSourceImpl(get(), get())
    }
    single<LanguageHistoryLocalDataSource> {
        LanguageHistoryLocalDataSourceImpl(get(), get())
    }
}

private val mappersModule = module {
    factory { LanguageCloudToDataMapper() }
    factory { TranslationCloudToDataMapper() }
    factory { TranslateRequestBodyDataToCloudMapper() }
    factory { TranslationDataToDomainMapper() }
    factory { LanguageDataToDomainMapper() }
    factory { TranslateRequestBodyDomainToDataMapper() }
    factory { TranslationHistoryItemToDataMapper() }
    factory { TranslationHistoryDataToDomainMapper() }
    factory { SelectedLanguageDataToDomainMapper() }
    factory { SelectedLanguageDomainToDataMapper() }
    factory { LanguageHistoryEntityToDataMapper() }
    factory { LanguageDomainToDataMapper() }
    factory { LanguageDataToEntityMapper() }
    factory { SelectedLanguageDtoToDataMapper() }
    factory { SelectedLanguageDataToDtoMapper() }
    factory { AppPreferencesDataToDtoMapper() }
    factory { AppPreferencesDtoToDataMapper() }
    factory { AppPreferencesToDataMapper() }
    factory { AppPreferencesDataDomainToMapper() }
}