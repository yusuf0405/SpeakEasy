package org.speak.easy.core

import android.app.Application
import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.StateFlow

actual class PlatformConfiguration constructor(
    val androidContext: Application,
    val activityContextFlow: StateFlow<ComponentActivity?>
)