package di

import org.koin.core.module.Module
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.speak.easy.core.ClipboardCopyManager
import org.speak.easy.core.TextSharingManager
import org.speak.easy.core.provideClipboardUpdateManager
import org.speak.easy.core.provideTextShareManager
import org.speak.easy.domain.di.domainModule
import org.speak.easy.history.HistoryFeatureImpl
import org.speak.easy.history.di.historyModule
import org.speak.easy.speech.speechFeatureModule
import org.speak.easy.translator.TranslatorDependencies
import org.speak.easy.translator.TranslatorFeatureImpl
import org.speak.easy.translator.di.translatorModule

val FEATURE_API_MODULES: StringQualifier = qualifier(name = "featureApiModules")

fun getAppModules(): List<Module> = listOf(
    translatorModule,
    historyModule,
    speechFeatureModule,
    featureApiModule,
    featureDependencyModule,
    domainModule,
    coreModule
)

private val coreModule = module {
    factory<TextSharingManager> { provideTextShareManager(get()) }
    factory<ClipboardCopyManager> { provideClipboardUpdateManager(get()) }
}

private val featureDependencyModule = module {
    single<TranslatorDependencies> {
        object : TranslatorDependencies {
            override fun getHistoryRoute(): String = ScreenRoutesProvider.getHistoryRoute()
        }
    }
}
private val featureApiModule = module {
    single(qualifier = FEATURE_API_MODULES) {
        listOf(
            HistoryFeatureImpl,
            TranslatorFeatureImpl,
        )
    }
}