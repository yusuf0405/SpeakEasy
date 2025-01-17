package org.speak.easy.settings.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.ui.extensions.SpacerHeight
import org.speak.easy.core.ui.extensions.clickableNoRipple
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.right_icon

@Composable
internal fun CategoryItem(
    category: Category,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Content(
        category = category,
        isRedContent = category.isRedContent,
        modifier = Modifier.clickableNoRipple { onClick() }
    ) {
        Icon(
            painter = painterResource(Res.drawable.right_icon),
            contentDescription = null,
            modifier = modifier
                .align(Alignment.CenterVertically)
                .size(SpeakEasyTheme.dimens.dp15),
            tint = SpeakEasyTheme.colors.iconsSecondary
        )
    }
}

@Composable
private fun Content(
    category: Category,
    isRedContent: Boolean = false,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = SpeakEasyTheme.dimens.dp12)
            .height(SpeakEasyTheme.dimens.dp40),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CategoryIcon(
            resource = category.resource,
            tint =
            if (isRedContent) {
                SpeakEasyTheme.colors.accentNegative
            } else {
                SpeakEasyTheme.colors.iconsPrimary
            }
        )
        Column(
            modifier = modifier.padding(
                start = SpeakEasyTheme.dimens.dp16
            )
        ) {
            Text(
                text = stringResource(category.title),
                style = SpeakEasyTheme.typography.bodyMedium.medium,
                color = if (isRedContent) {
                    SpeakEasyTheme.colors.accentNegative
                } else {
                    SpeakEasyTheme.colors.textPrimary
                },
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp2)
            if (category.description != null) {
                Text(
                    text = stringResource(category.description),
                    style = SpeakEasyTheme.typography.bodySmall.light,
                    color = if (isRedContent) {
                        SpeakEasyTheme.colors.accentNegative
                    } else {
                        SpeakEasyTheme.colors.textSecondary
                    }
                )
            }
        }
        Spacer(
            modifier = modifier
                .weight(1f)
                .fillMaxHeight()
        )
        content()
    }
}

@Composable
private fun CategoryIcon(
    tint: Color,
    resource: DrawableResource,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(SpeakEasyTheme.dimens.dp36)
            .clip(CircleShape)
            .background(SpeakEasyTheme.colors.backgroundSecondary),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(SpeakEasyTheme.dimens.dp18),
            painter = painterResource(resource),
            contentDescription = null,
            tint = tint
        )
    }
}