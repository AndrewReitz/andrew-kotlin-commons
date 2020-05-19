package cash.andrew.kotlin.coroutines

import kotlinx.coroutines.CoroutineScope

expect fun run(body: suspend CoroutineScope.() -> Unit)
