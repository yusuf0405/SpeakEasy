package org.speak.easy.core.navigation

expect class BottomNavigationItemsFactory constructor() {
    fun create(): List<BottomNavigationItem>
}