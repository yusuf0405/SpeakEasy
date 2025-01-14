package org.speak.easy.data.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module
import org.speak.easy.core.DispatcherProvider
import org.speak.easy.core.provideDispatcher
import org.speak.easy.data.BuildKonfig
import org.speak.easy.data.cloud.mappers.LanguageCloudToDataMapper
import org.speak.easy.data.cloud.mappers.TranslationCloudToDataMapper
import org.speak.easy.data.cloud.service.DeeplCloudService
import org.speak.easy.data.cloud.service.DeeplCloudServiceImpl
import org.speak.easy.data.cloud.source.TranslatorCloudDataSource
import org.speak.easy.data.cloud.source.TranslatorCloudDataSourceImpl
import org.speak.easy.data.local.DatabaseFactory
import org.speak.easy.data.local.data.store.SelectedLanguageDataStore
import org.speak.easy.data.local.database.LanguageHistoryDao
import org.speak.easy.data.local.database.TranslationHistoryDao
import org.speak.easy.data.local.database.TranslationHistoryDatabase
import org.speak.easy.data.local.mappers.LanguageHistoryEntityToDataMapper
import org.speak.easy.data.local.mappers.TranslationHistoryItemToDataMapper
import org.speak.easy.data.local.source.LanguageHistoryLocalDataSource
import org.speak.easy.data.local.source.LanguageHistoryLocalDataSourceImpl
import org.speak.easy.data.local.source.TranslationHistoryLocalDataSource
import org.speak.easy.data.local.source.TranslationHistoryLocalDataSourceImpl
import org.speak.easy.data.mappers.LanguageDataToDomainMapper
import org.speak.easy.data.mappers.LanguageDataToEntityMapper
import org.speak.easy.data.mappers.LanguageDomainToDataMapper
import org.speak.easy.data.mappers.SelectedLanguageDataToDomainMapper
import org.speak.easy.data.mappers.SelectedLanguageDomainToDataMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDataToCloudMapper
import org.speak.easy.data.mappers.TranslateRequestBodyDomainToDataMapper
import org.speak.easy.data.mappers.TranslationDataToDomainMapper
import org.speak.easy.data.mappers.TranslationHistoryDataToDomainMapper
import org.speak.easy.data.repositories.LanguageHistoryRepositoryImpl
import org.speak.easy.data.repositories.TranslationRepositoryImpl
import org.speak.easy.domain.LanguageHistoryRepository
import org.speak.easy.domain.TranslationRepository

private const val DEFAULT_REQUEST_TIMEOUT_MILLS = 10_000L

fun getSharedModule(): List<Module> = listOf(
    clientModule,
    dataModule,
    mappersModule,
    databaseModule
)

private val dataModule = module {
    factory<DispatcherProvider> { provideDispatcher() }
    single<DeeplCloudService> { DeeplCloudServiceImpl(get()) }
    single<TranslatorCloudDataSource> { TranslatorCloudDataSourceImpl(get(), get()) }
    single<LanguageHistoryRepository> {
        LanguageHistoryRepositoryImpl(
            get(), get(),
            get(), get(), get()
        )
    }
    single<TranslationRepository> {
        TranslationRepositoryImpl(
            get(), get(), get(), get(), get(), get(),
            get(), get(), get(), get(), get(), get(), get()
        )
    }
}

internal val databaseModule = module {
    single { DatabaseFactory(get()) }
    single<TranslationHistoryDatabase> { get<DatabaseFactory>().createRoomDatabase() }
    single<SelectedLanguageDataStore> { get<DatabaseFactory>().createSelectedLanguageDataStore() }
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
}

private val clientModule = module {
    single {
        HttpClient {
            defaultRequest {
                url.takeFrom(BuildKonfig.BASE_URL)
                contentType(ContentType.Application.Json)
                header("Authorization", "DeepL-Auth-Key ${BuildKonfig.API_KEY}")
            }
            install(HttpCache)
            install(HttpTimeout) {
                requestTimeoutMillis = DEFAULT_REQUEST_TIMEOUT_MILLS
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        useAlternativeNames = false
                        prettyPrint = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
        }
    }
}
