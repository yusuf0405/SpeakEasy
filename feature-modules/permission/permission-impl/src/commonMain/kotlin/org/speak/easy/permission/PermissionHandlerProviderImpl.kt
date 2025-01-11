package org.speak.easy.permission

import androidx.compose.runtime.Composable
import org.speak.easy.permission.api.PermissionCallback
import org.speak.easy.permission.api.PermissionHandler
import org.speak.easy.permission.api.PermissionHandlerProvider

class PermissionHandlerProviderImpl : PermissionHandlerProvider {

    @Composable
    override fun get(callback: PermissionCallback): PermissionHandler {
        return createPermissionsManager(callback)
    }
}