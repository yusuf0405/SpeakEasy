package org.speak.easy.core.ui.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import org.speak.easy.core.analytics.AnalyticsEvent
import org.speak.easy.core.analytics.AnalyticsEvent.Param
import org.speak.easy.core.analytics.AnalyticsHelper
import org.speak.easy.core.analytics.LocalAnalyticsHelper

fun AnalyticsHelper.logScreenView(screenName: String) {
    logEvent(
        AnalyticsEvent(
            type = AnalyticsEvent.Types.SCREEN_VIEW,
            extras = listOf(
                Param(AnalyticsEvent.ParamKeys.SCREEN_NAME, screenName),
            )
        )
    )
}

/**
 * A side-effect which records a screen view event.
 */
@Composable
fun TrackScreenViewEvent(
    screenName: String,
    analyticsHelper: AnalyticsHelper = LocalAnalyticsHelper.current,
) = DisposableEffect(Unit) {
    analyticsHelper.logScreenView(screenName)
    onDispose {}
}
