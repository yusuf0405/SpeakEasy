package org.speak.easy.core

interface Mapper<in From, out To> {
    fun map(from: From): To
}