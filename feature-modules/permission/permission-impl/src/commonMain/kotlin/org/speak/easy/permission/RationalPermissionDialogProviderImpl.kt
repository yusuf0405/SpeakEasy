package org.speak.easy.permission

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import org.speak.easy.core.designsystem.SpeakEasyTheme
import org.speak.easy.core.designsystem.colors.ControlPrimaryActive
import org.speak.easy.permission.api.RationalPermissionDialogProvider

class RationalPermissionDialogProviderImpl : RationalPermissionDialogProvider {

    @Composable
    override fun get(
        title: String,
        description: String,
        confirmText: String,
        dismissText: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
        modifier: Modifier
    ) {
        AlertDialog(
            modifier = modifier.zIndex(99f),
            containerColor = SpeakEasyTheme.colors.backgroundPrimaryElevated,
            tonalElevation = SpeakEasyTheme.dimens.dp6,
            title = {
                Text(
                    text = title,
                    style = SpeakEasyTheme.typography.titleSmall.medium,
                    color = SpeakEasyTheme.colors.textPrimary
                )
            },
            text = {
                Text(
                    text = description,
                    style = SpeakEasyTheme.typography.bodyMedium.light,
                    color = SpeakEasyTheme.colors.textPrimary
                )
            },
            onDismissRequest = {},
            dismissButton = {
                TextButton(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = SpeakEasyTheme.colors.textSecondary,
                        containerColor = SpeakEasyTheme.colors.backgroundModal
                    )
                ) {
                    Text(
                        text = dismissText,
                        style = SpeakEasyTheme.typography.bodySmall.bold,
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = onConfirm,
                    colors = ButtonDefaults.buttonColors(
                        contentColor = ControlPrimaryActive,
                        containerColor = SpeakEasyTheme.colors.backgroundModal
                    ),

                    ) {
                    Text(
                        text = confirmText,
                        style = SpeakEasyTheme.typography.bodySmall.bold,
                    )
                }
            }
        )
    }
}
