package org.speak.easy.permission

import androidx.compose.runtime.Composable
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandler

expect class PermissionsManager(callback: PermissionCallback) : PermissionHandler

@Composable
expect fun createPermissionsManager(callback: PermissionCallback): PermissionsManager