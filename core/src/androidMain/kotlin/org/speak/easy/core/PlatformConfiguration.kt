package org.speak.easy.core

import android.content.Context
import androidx.activity.ComponentActivity
import kotlinx.coroutines.flow.StateFlow

actual class PlatformConfiguration constructor(
    val androidContext: Context,
    val activityContextFlow: StateFlow<ComponentActivity?>
)