package cash.andrew.kotlin.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

actual fun runTest(body: suspend CoroutineScope.() -> Unit): dynamic = GlobalScope.promise {
  body()
}
