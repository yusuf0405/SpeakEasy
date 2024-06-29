package org.speak.easy

import App
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import di.getAppModules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.speak.easy.core.PlatformConfiguration
import org.speak.easy.data.di.getSharedModule

class AndroidApp : Application() {

    private val activityContextFlow: MutableStateFlow<Context?> = MutableStateFlow(null)

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@AndroidApp)
            modules(getAppModules() + getSharedModule() + initPlatformConfiguration())
        }
    }

    fun updateActivityContext(context: Context) {
        activityContextFlow.tryEmit(context)
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