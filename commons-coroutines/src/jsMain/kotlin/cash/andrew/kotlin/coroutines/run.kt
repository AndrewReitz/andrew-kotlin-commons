package cash.andrew.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun run(body: suspend CoroutineScope.() -> Unit): dynamic = GlobalScope.promise { body() }
