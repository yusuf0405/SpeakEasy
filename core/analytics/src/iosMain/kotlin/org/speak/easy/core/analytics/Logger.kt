package org.speak.easy.core.analytics

import platform.Foundation.NSLog
import platform.darwin.OS_LOG_DEFAULT

actual class Logger {
    actual fun log(tag: String, event: AnalyticsEvent) {
        event.extras.forEach { param: AnalyticsEvent.Param ->
            NSLog("${param.key}: ${param.value}", OS_LOG_DEFAULT)
        }
    }
}