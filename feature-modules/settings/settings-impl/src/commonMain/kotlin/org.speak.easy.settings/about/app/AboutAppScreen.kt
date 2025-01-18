package org.speak.easy.settings.about.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.ui.extensions.SpacerHeight
import org.speak.easy.core.ui.extensions.SpacerWidth
import org.speak.easy.core.ui.extensions.clickableNoRipple
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.email
import speakeasy.core.ui.generated.resources.email_item
import speakeasy.core.ui.generated.resources.telegram
import speakeasy.core.ui.generated.resources.telegram_item
import speakeasy.core.ui.generated.resources.version

@Composable
internal fun AboutAppScreen(
    uiState: AboutAppInfo,
    openTelegram: (String) -> Unit,
    openEmail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .background(SpeakEasyTheme.colors.backgroundPrimary)
            .fillMaxSize()
            .padding(horizontal = SpeakEasyTheme.dimens.dp16)
    ) {
        SpacerHeight(SpeakEasyTheme.dimens.dp20)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(uiState.appName),
            style = SpeakEasyTheme.typography.titleLarge.bold,
            color = SpeakEasyTheme.colors.textPrimary,
            textAlign = TextAlign.Center
        )
        SpacerHeight(SpeakEasyTheme.dimens.dp16)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "${stringResource(Res.string.version)}: ${uiState.version}",
            style = SpeakEasyTheme.typography.bodyMedium.medium,
            color = SpeakEasyTheme.colors.textSecondary,
            textAlign = TextAlign.Center
        )
        SpacerHeight(SpeakEasyTheme.dimens.dp16)
        Text(
            text = stringResource(uiState.description),
            style = SpeakEasyTheme.typography.bodyExtraMedium.light,
            color = SpeakEasyTheme.colors.textPrimary,
            lineHeight = SpeakEasyTheme.dimens.sp24
        )
        SpacerHeight(SpeakEasyTheme.dimens.dp12)
        uiState.telegram?.let {
            ContactItem(
                modifier = Modifier.clickableNoRipple { openTelegram(uiState.telegram) },
                text = stringResource(Res.string.telegram),
                contact = it,
                painter = painterResource(Res.drawable.telegram_item)
            )
        }
        uiState.email?.let {
            ContactItem(
                modifier = Modifier.clickableNoRipple { openEmail(uiState.email) },
                text = stringResource(Res.string.email),
                contact = it,
                painter = painterResource(Res.drawable.email_item)
            )
        }
    }
}

@Composable
private fun ContactItem(
    text: String,
    contact: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(vertical = SpeakEasyTheme.dimens.dp12)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier.size(SpeakEasyTheme.dimens.dp48)
        )
        SpacerWidth(SpeakEasyTheme.dimens.dp16)
        Column {
            Text(
                text = text,
                style = SpeakEasyTheme.typography.bodyExtraMedium.medium,
                color = SpeakEasyTheme.colors.textPrimary
            )
            SpacerHeight(SpeakEasyTheme.dimens.dp2)
            Text(
                text = contact,
                style = SpeakEasyTheme.typography.bodyMedium.light,
                color = SpeakEasyTheme.colors.textSecondary
            )
        }
    }
}