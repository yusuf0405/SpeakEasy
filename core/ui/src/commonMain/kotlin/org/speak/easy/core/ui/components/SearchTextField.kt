package org.speak.easy.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.stringResource
import org.speak.easy.core.designsystem.SpeakEasyTheme
import speakeasy.core.ui.generated.resources.Res
import speakeasy.core.ui.generated.resources.enter_the_text

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        textStyle = SpeakEasyTheme.typography.bodyMedium.medium,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = SpeakEasyTheme.colors.backgroundModal,
            unfocusedContainerColor = SpeakEasyTheme.colors.backgroundModal,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = SpeakEasyTheme.colors.textPrimary,
            unfocusedTextColor = SpeakEasyTheme.colors.textPrimary,
        ),
        placeholder = {
            Text(
                text = stringResource(Res.string.enter_the_text),
                color = SpeakEasyTheme.colors.textSecondary,
                style = SpeakEasyTheme.typography.bodyMedium.medium
            )
        }
    )

}