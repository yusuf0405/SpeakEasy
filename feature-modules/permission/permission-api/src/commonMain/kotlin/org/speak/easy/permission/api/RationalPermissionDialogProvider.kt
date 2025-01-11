package org.speak.easy.permission.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface RationalPermissionDialogProvider {

    @Composable
    fun get(
        title: String,
        description: String,
        confirmText: String,
        dismissText: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
        modifier: Modifier
    )
}