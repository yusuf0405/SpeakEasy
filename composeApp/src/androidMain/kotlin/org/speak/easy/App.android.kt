package org.speak.easy

import android.app.Application
import androidx.activity.ComponentActivity
import org.speak.easy.di.getAppModules
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.di.getSharedModule

class AndroidApp : Application() {

    private val activityContextFlow: MutableStateFlow<ComponentActivity?> = MutableStateFlow(null)

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@AndroidApp)
            modules(getAppModules() + getSharedModule() + initPlatformConfiguration())
        }
    }

    fun updateActivityContext(activity: ComponentActivity) {
        activityContextFlow.tryEmit(activity)
    }

    private fun initPlatformConfiguration() = module {
        single {
            PlatformConfiguration(
                androidContext = applicationContext,
                activityContextFlow = activityContextFlow
            )
        }
    }

    companion object {
        lateinit var INSTANCE: AndroidApp
    }
}