package cash.andrew.kotlin.common

// bad file name, but will keep it until this file has more stuff and then things will get pulled out
// into more specific files.

/**
 * Add after a when statement to force it to be exhaustive with out having to set
 * the result to a variable.
 *
 * ex.
 * ```
 * when(result) {
 *   is Success -> println("yay")
 *   is Failure -> println(":(")
 * }.exhaustive
 * ```
 *
 * Now if any new subtypes are added to the result class exhaustive would cause you to get a compiler failure
 * and you can handle the new types.
 */
val <T> T.exhaustive: T get() = this

/**
 * Tries to run the function [block] [times] number of times catching [times]-1 exceptions and trying again.
 * On the last execution if there is an exception it will be bubbled up to the caller.
 *
 * @param times default 3, the number of times the block will be tried.
 * @param block code block that will be run.
 */
inline fun <T> retry(times: Int = 3, block: () -> T): T {
  repeat(times - 1) {
    try {
      return block()
    } catch (e: Exception) { }
  }

  return block()
}
