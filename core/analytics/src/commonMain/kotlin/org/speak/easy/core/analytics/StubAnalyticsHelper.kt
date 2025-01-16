package org.speak.easy.core.analytics

private const val TAG = "Joseph"

internal class StubAnalyticsHelper : AnalyticsHelper {
    private val logger by lazy { Logger() }

    override fun logEvent(event: AnalyticsEvent) {
        logger.log(TAG, event)
    }
}
