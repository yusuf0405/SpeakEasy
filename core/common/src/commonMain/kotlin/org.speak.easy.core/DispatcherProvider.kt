package org.speak.easy.core

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    val io: CoroutineDispatcher

    val main: CoroutineDispatcher
}

expect fun provideDispatcher(): DispatcherProvider