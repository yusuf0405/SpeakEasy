package org.speak.easy.core.navigation.di

import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.speak.easy.core.navigation.BottomNavigationItemsFactory
import org.speak.easy.core.navigation.DefaultNavigator
import org.speak.easy.core.navigation.Navigator

val BOTTOM_NAVIGATION_ITEMS: StringQualifier = qualifier(name = "bottom_items")

val navigationModule = module {
    single(qualifier = BOTTOM_NAVIGATION_ITEMS) {
        BottomNavigationItemsFactory().create()
    }

    single<Navigator> { DefaultNavigator() }
}