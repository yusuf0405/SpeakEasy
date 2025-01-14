package org.speak.easy.navigation

expect class BottomNavigationItemsFactory constructor() {
    fun create(): List<BottomNavigationItem>
}