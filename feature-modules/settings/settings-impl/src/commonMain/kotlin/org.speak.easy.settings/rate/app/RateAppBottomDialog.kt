package org.speak.easy.settings.rate.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.colors.ControlPrimaryActive
import org.speak.easy.core.ui.extensions.SpacerHeight
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.cancel_icon
import speakeasy.core.ui.generated.resources.rate_app
import speakeasy.core.ui.generated.resources.rate_app_description
import speakeasy.core.ui.generated.resources.rating_app_poster


@Composable
internal fun RateAppBottomDialog(
    onDismiss: () -> Unit,
    onButton: () -> Unit,
    sheetState: SheetState,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        tonalElevation = SpeakEasyTheme.dimens.dp4,
        sheetState = sheetState,
        containerColor = SpeakEasyTheme.colors.backgroundPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SpeakEasyTheme.dimens.dp16)
                .verticalScroll(rememberScrollState()),
        ) {
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            Icon(
                modifier = Modifier
                    .size(SpeakEasyTheme.dimens.dp24)
                    .clickable { onDismiss() },
                painter = painterResource(Res.drawable.cancel_icon),
                contentDescription = ""
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            Text(
                text = stringResource(Res.string.rate_app),
                style = SpeakEasyTheme.typography.titleMedium.bold,
                color = SpeakEasyTheme.colors.textPrimary
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            Text(
                text = stringResource(Res.string.rate_app_description),
                style = SpeakEasyTheme.typography.bodyExtraMedium.light,
                color = SpeakEasyTheme.colors.textSecondary,
                lineHeight = SpeakEasyTheme.dimens.sp24
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(Res.drawable.rating_app_poster),
                contentDescription = "",
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp24)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(SpeakEasyTheme.dimens.dp12))
                    .background(ControlPrimaryActive)
                    .clickable {
                        onButton()
                        onDismiss()
                    },
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = SpeakEasyTheme.dimens.dp12),
                    text = stringResource(Res.string.rate_app),
                    style = SpeakEasyTheme.typography.bodyMedium.bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
            SpacerHeight(SpeakEasyTheme.dimens.dp16)
        }
    }
}