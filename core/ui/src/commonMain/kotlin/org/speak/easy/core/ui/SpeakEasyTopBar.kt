package org.speak.easy.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.dimens.ExtraLargeSpacing
import org.speak.easy.core.designsystem.dimens.LargeSpacing
import org.speak.easy.core.designsystem.dimens.MediumElevation

@Composable
fun SpeakEasyTopBar(
    title: String,
    modifier: Modifier = Modifier,
    endIcon: Painter? = null,
    startIcon: Painter? = null,
    contentAlignment: Alignment = Alignment.Center,
    onEndClick: () -> Unit = {},
    onStartClick: () -> Unit = {},
    endIconVisible: Boolean = true,
) {
    Surface(
        tonalElevation = MediumElevation,
        shadowElevation = MediumElevation,
        color = SpeakEasyTheme.colors.backgroundModal
    ) {
        Box(
            modifier = modifier
                .statusBarsPadding()
                .padding(horizontal = ExtraLargeSpacing)
                .padding(vertical = LargeSpacing)
                .fillMaxWidth(),
            contentAlignment = contentAlignment
        ) {
            startIcon?.apply {
                AppBarIcon(
                    modifier = Modifier.align(Alignment.CenterStart),
                    painter = startIcon,
                    onClick = onStartClick
                )
            }

            Text(
                text = title,
                style = SpeakEasyTheme.typography.titleExtraMedium.medium,
                textAlign = TextAlign.Center
            )

            if (endIcon != null && endIconVisible) {
                AppBarIcon(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    painter = endIcon,
                    onClick = onEndClick
                )
            }
        }
    }
}

@Composable
fun AppBarIcon(
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    background: Color = Color.Transparent,
    isVisibility: Boolean = true
) {
    Box(
        modifier = modifier.border(
            width = SpeakEasyTheme.dimens.dp1,
            shape = CircleShape,
            color = if (isVisibility) SpeakEasyTheme.colors.iconsSecondary
            else Color.Transparent
        )
            .size(SpeakEasyTheme.dimens.dp32)
            .clip(CircleShape)
            .alpha(if (isVisibility) 1f else 0f)
            .background(background)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(18.dp),
            painter = painter,
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
        )
    }
}


