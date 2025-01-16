package org.speak.easy.core.analytics

expect class Logger constructor() {
    fun log(tag: String, event: AnalyticsEvent)
}