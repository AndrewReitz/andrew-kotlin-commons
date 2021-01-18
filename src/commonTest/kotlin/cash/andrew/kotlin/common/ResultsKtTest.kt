package cash.andrew.kotlin.common

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

class ResultsKtTest {
  @JsName("resultFrom_should_return_failure")
  @Test
  fun `resultFrom should return failure`() {
    val expected = Exception("Oh no")

    val result = resultFrom { throw expected }

    assertTrue(result is Failure)
    assertEquals(expected, result.reason)
  }

  @JsName("resultFrom_should_return_success")
  @Test
  fun `resultFrom should return success`() {
    val expected = "yay!"

    val result = resultFrom { expected }

    assertTrue(result is Success)
    assertEquals(expected, result.value)
  }

  @JsName("map_should_perform_the_expected_transform")
  @Test
  fun `map should perform the expected transform`() {
    val success = Result.success("test").map { it.capitalize() }
    assertEquals(Result.success("Test"), success)

    val failure = Result.failure("failed").map(String::capitalize)
    assertEquals(Result.failure("failed"), failure)
  }

  @JsName("flatMap_should_perform_the_expected_transform")
  @Test
  fun `flatMap should perform the expected transform`() {
    fun doStuff(s: String): Result<Int, String> = Result.success(s.count())

    val success = Result.success("yay!").flatMap(::doStuff)
    assertEquals(Result.success(4), success)

    val failure = Result.failure("failed").flatMap(::doStuff)
    assertEquals(Result.failure("failed"), failure)
  }

  @JsName("flatMapFailure_should_perform_expected_transform")
  @Test
  fun `flatMapFailure should perform expected transform`() {
    fun doStuff(s: String): Result<Int, String> = Result.success(s.count())

    val success = Result.success("test").flatMapFailure(::doStuff)
    assertEquals(Result.success("test"), success)

    val failure = Result.failure("failed").flatMapFailure(::doStuff)
    assertEquals(Result.success(6), failure)
  }

  @JsName("mapFailure_should_perform_expected_transform")
  @Test
  fun `mapFailure should perform expected transform`() {
    val success = Result.success("Yay!").mapFailure { "Oh no!" }
    assertEquals(Result.success("Yay!"), success)

    val failure = Result.failure(Exception("Oh no!")).mapFailure { "This is fine" }
    assertEquals(Result.failure("This is fine"), failure)
  }

  @JsName("get_should_return_value")
  @Test
  fun `get should return value`() {
    val success = Result.success("Yay!").get()
    assertEquals("Yay!", success)

    val failure = Result.failure("Oh no!").get()
    assertEquals("Oh no!", failure)
  }

  @JsName("onFailure_should_return_successful_value_or_fail")
  @Test
  fun `onFailure should return successful value or fail`() {
    val success = Result.success("Yay!").onFailure { throw Exception("Oh no") }
    assertEquals("Yay!", success)

    assertFails("Oh no!") {
      val fail: Result<String, Exception> = Result.failure(Exception("Oh no!"))
      fail.onFailure { throw it.reason }
    }
  }

  @JsName("recover_should_return_successful_value_or_default_recover_value")
  @Test
  fun `recover should return successful value or default recover value`() {
    val success = Result.success("Yay!").recover { "Oh No!" }
    assertEquals("Yay!", success)

    val failure = Result.failure(Exception("Oh No!")).recover { it.message }
    assertEquals("Oh No!", failure)
  }

  @JsName("peek_should_be_passed_success_value")
  @Test
  fun `peek should be passed success value`() {
    var result = ""
    Result.success("Yay!").doOnSuccess { result = it }
    assertEquals("Yay!", result)

    val temp: Result<String, Exception> = Result.failure(Exception("Oh No!"))
    temp.doOnSuccess { result = it }
    assertEquals("Yay!", result)
  }

  @JsName("peekFailure_should_be_passed_failure_reason")
  @Test
  fun `peekFailure should be passed failure reason`() {
    var result = ""
    val temp: Result<String, Exception> = Result.success("Yay!")
    temp.doOnFailure { result = it.message.orEmpty() }
    assertEquals("", result)

    Result.failure(Exception("Oh No!")).doOnFailure { result = it.message.orEmpty() }
    assertEquals("Oh No!", result)
  }
}
