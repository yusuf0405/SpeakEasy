package org.speak.easy.settings.category

expect class CategoryFactory constructor() {
    fun categories(): List<Category>
    fun moreCategories(): List<Category>
}