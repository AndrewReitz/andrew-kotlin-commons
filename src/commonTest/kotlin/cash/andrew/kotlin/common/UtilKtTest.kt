package cash.andrew.kotlin.common

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class UtilKtTest {
  @JsName("should_fail_once_then_succeed")
  @Test
  fun `should fail once then succeed`() {
    var count = 0
    val result = retry {
      if (++count == 2) {
        return@retry "It works"
      }

      throw Exception("Doesn't work")
    }

    assertEquals(2, count)
    assertEquals("It works", result)
  }

  @JsName("should_fail_3_times")
  @Test
  fun `should fail 3 times`() {
    var count = 0
    assertFails("Doesn't work") {
      retry<String> {
        ++count
        throw Exception("Doesn't work")
      }
    }

    assertEquals(3, count)
  }
}
