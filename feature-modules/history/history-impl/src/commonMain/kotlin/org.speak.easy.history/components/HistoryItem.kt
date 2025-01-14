package org.speak.easy.history.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import org.jetbrains.compose.resources.painterResource
import org.speak.easy.history.models.HistoryModel
import org.speak.easy.core.ui.extensions.SpacerHeight
import org.speak.easy.core.ui.extensions.SpacerWidth
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.colors.ControlPrimaryActiveDark
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.arrow_left
import speakeasy.core.ui.generated.resources.more

@Composable
internal fun HistoryItem(
    item: HistoryModel,
    dismissState: SwipeToDismissBoxState,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(SpeakEasyTheme.dimens.dp14)

    SwipeToDismissBox(
        modifier = modifier,
        state = dismissState,
        backgroundContent = {
            SwipeBackgroundContent(
                dismissState = dismissState,
                shape = shape
            )
        },
        enableDismissFromStartToEnd = false
    ) {
        Card(
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = SpeakEasyTheme.dimens.dp2
            ),
            colors = CardDefaults.cardColors(
                containerColor = SpeakEasyTheme.colors.backgroundModal
            ),
            shape = shape
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(SpeakEasyTheme.dimens.dp12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.sourceLanguage,
                            style = SpeakEasyTheme.typography.bodySmall.medium,
                            color = SpeakEasyTheme.colors.textSecondary
                        )
                        SpacerWidth(SpeakEasyTheme.dimens.dp4)
                        Image(
                            modifier = Modifier.size(SpeakEasyTheme.dimens.dp12),
                            painter = painterResource(Res.drawable.arrow_left),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.textSecondary)
                        )
                        SpacerWidth(SpeakEasyTheme.dimens.dp4)
                        Text(
                            text = item.targetLanguage,
                            style = SpeakEasyTheme.typography.bodySmall.medium,
                            color = SpeakEasyTheme.colors.textSecondary
                        )
                    }
                    SpacerHeight(SpeakEasyTheme.dimens.dp8)
                    Text(
                        text = item.sourceText,
                        style = SpeakEasyTheme.typography.bodyLarge.medium,
                        color = SpeakEasyTheme.colors.textPrimary
                    )
                    SpacerHeight(SpeakEasyTheme.dimens.dp4)
                    Text(
                        text = item.targetText,
                        style = SpeakEasyTheme.typography.bodyMedium.medium,
                        color = SpeakEasyTheme.colors.textPrimary
                    )
                }
//                Image(
//                    modifier = Modifier
//                        .size(SpeakEasyTheme.dimens.dp24)
//                        .clickable { onMoreClick() },
//                    painter = painterResource(Res.drawable.more),
//                    contentDescription = null,
//                    colorFilter = ColorFilter.tint(color = SpeakEasyTheme.colors.iconsPrimary)
//                )
//                SpacerWidth(SpeakEasyTheme.dimens.dp8)
            }
        }
    }
}

@Composable
fun SwipeBackgroundContent(
    dismissState: SwipeToDismissBoxState,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> ControlPrimaryActiveDark
        else -> Color.Transparent
    }
    val alignment = Alignment.CenterEnd

    val scale by animateFloatAsState(
        if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.80f else 1f
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(shape)
            .background(color)
            .padding(horizontal = SpeakEasyTheme.dimens.dp40),
        contentAlignment = alignment
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete Icon",
            modifier = Modifier.scale(scale),
            tint = Color.White
        )
    }
}