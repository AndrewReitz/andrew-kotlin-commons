package cash.andrew.kotlin.test

import kotlinx.coroutines.CoroutineScope

expect fun runTest(body: suspend CoroutineScope.() -> Unit)
