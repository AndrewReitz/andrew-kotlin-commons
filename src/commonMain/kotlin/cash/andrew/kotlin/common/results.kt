package cash.andrew.kotlin.common

sealed class Result<out T, out E> {
  companion object {
    fun <T> success(value: T): Result<T, Nothing> = Success(value)
    fun <E> failure(reason: E): Result<Nothing, E> = Failure(reason)
  }
}

data class Success<out T>(val value: T) : Result<T, Nothing>()
data class Failure<out E>(val reason: E) : Result<Nothing, E>()

/**
 * Calls the specified function [block] and returns the result in a [Success] if executes successfully,
 * otherwise catches any thrown exceptions and returns a [Failure].
 */
inline fun <T> resultFrom(block: () -> T): Result<T, Exception> {
  return try {
    Success(block())
  } catch (e: Exception) {
    Failure(e)
  }
}

/** Return a result containing the value of applying the given [transform] function to [Success.value]. */
inline fun <T, R, E> Result<T, E>.map(transform: (T) -> R): Result<R, E> =
    flatMap { value -> Success(transform(value)) }

/** Return a result where the value is the result of [transform] being invoked on [Success.value]. */
inline fun <T, R, E> Result<T, E>.flatMap(transform: (T) -> Result<R, E>): Result<R, E> =
    when (this) {
      is Success<T> -> transform(value)
      is Failure<E> -> this
    }

/** Return a result where the value is the result of [transform] being invoked on [Failure.reason]. */
inline fun <T, E, R> Result<T, E>.flatMapFailure(transform: (E) -> Result<T, R>): Result<T, R> = when (this) {
  is Success<T> -> this
  is Failure<E> -> transform(reason)
}

/** Return a result containing the value of applying the given [transform] function to [Failure.reason]. */
inline fun <T, E, R> Result<T, E>.mapFailure(transform: (E) -> R): Result<T, R> =
    flatMapFailure { reason -> Failure(transform(reason)) }

/**
 * Unwrap a Result in which both the success and failure values have the same type, returning a plain value.
 */
fun <T> Result<T, T>.get() = when (this) {
  is Success<T> -> value
  is Failure<T> -> reason
}

/**
 * Unwrap a Result, by returning the success value or calling [block] on failure to abort from the current function.
 * ex: `result.onFailure { throw it.reason }`
 * or short circuit a function with `result.onFailure { return it.reason }`
 */
inline fun <T, E> Result<T, E>.onFailure(block: (Failure<E>) -> Nothing): T = when (this) {
  is Success<T> -> value
  is Failure<E> -> block(this)
}

/**
 * Unwrap a Result by returning the success value or calling _failureToValue_ to mapping the failure reason to a plain value.
 */
inline fun <S, T : S, U : S, E> Result<T, E>.recover(action: (E) -> U): S =
    mapFailure(action).get()

/**
 * Perform a side effect with the success value.
 */
inline fun <T, E> Result<T, E>.peek(f: (T) -> Unit) =
    apply { if (this is Success<T>) f(value) }

/**
 * Perform a side effect with the failure reason.
 */
inline fun <T, E> Result<T, E>.peekFailure(f: (E) -> Unit) =
    apply { if (this is Failure<E>) f(reason) }
