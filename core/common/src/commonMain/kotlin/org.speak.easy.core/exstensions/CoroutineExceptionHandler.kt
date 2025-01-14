package org.speak.easy.core.exstensions

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

inline fun CoroutineScope.launchSafe(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    crossinline onError: (Throwable) -> Unit = { println(it.stackTraceToString()) },
    crossinline safeAction: suspend CoroutineScope.() -> Unit,
) = this.launch(dispatcher) {
    try {
        safeAction()
    } catch (e: CancellationException) {
        throw e
    } catch (e: Throwable) {
        println(e.stackTraceToString())
        onError(e)
    }
}

inline fun <T> Flow<T>.onError(crossinline action: (Throwable) -> Unit) = this.catch { action(it) }