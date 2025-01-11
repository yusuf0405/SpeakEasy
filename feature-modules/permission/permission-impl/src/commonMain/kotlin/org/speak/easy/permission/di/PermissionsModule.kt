package org.speak.easy.permission.di

import org.koin.dsl.module
import org.speak.easy.permission.api.PermissionHandlerProvider
import org.speak.easy.permission.PermissionHandlerProviderImpl
import org.speak.easy.permission.RationalPermissionDialogProviderImpl
import org.speak.easy.permission.api.RationalPermissionDialogProvider

val permissionsModule = module {
    single<PermissionHandlerProvider> {
        PermissionHandlerProviderImpl()
    }
    factory<RationalPermissionDialogProvider> {
        RationalPermissionDialogProviderImpl()
    }
}