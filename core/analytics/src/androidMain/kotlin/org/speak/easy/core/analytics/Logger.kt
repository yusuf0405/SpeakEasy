package org.speak.easy.core.analytics

import android.util.Log

actual class Logger {
    actual fun log(tag: String, event: AnalyticsEvent) {
        event.extras.forEach { param: AnalyticsEvent.Param ->
            Log.i(tag, "${param.key}: ${param.value}")
        }
    }
}