package org.speak.easy.ui.components.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import org.speak.easy.ui.core.theme.SpeakEasyTheme

@Composable
fun ProgressBar(
    size: Dp = SpeakEasyTheme.dimens.dp24,
    alignment: Alignment = Alignment.CenterStart,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(size)
                .align(alignment),
            strokeWidth = SpeakEasyTheme.dimens.dp2
        )
    }
}