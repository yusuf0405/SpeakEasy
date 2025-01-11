package org.speak.easy.permission.api

import androidx.compose.runtime.Composable

interface PermissionHandlerProvider {

    @Composable
    fun get(callback: PermissionCallback): PermissionHandler
}