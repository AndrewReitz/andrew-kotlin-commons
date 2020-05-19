package cash.andrew.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking

actual fun run(body: suspend CoroutineScope.() -> Unit) = runBlocking { body() }
