package org.speak.easy.permission.api

import androidx.compose.runtime.Composable

interface PermissionHandler {
    @Composable
    fun askPermission(type: PermissionType)

    @Composable
    fun isPermissionGranted(permission: PermissionType): Boolean

    @Composable
    fun launchSettings()
}