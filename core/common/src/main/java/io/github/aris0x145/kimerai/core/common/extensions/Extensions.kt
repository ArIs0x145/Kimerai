package io.github.aris0x145.kimerai.core.common.extensions

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch // Make sure this is kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.map // Import the map operator
import io.github.aris0x145.kimerai.core.common.result.Result

/**
 * String 擴展函數
 */
fun String.capitalizeFirstLetter(): String {
    return if (isNotEmpty()) {
        this[0].uppercase() + substring(1)
    } else {
        this
    }
}

fun String?.orEmpty(defaultValue: String = ""): String {
    return this ?: defaultValue
}

/**
 * Flow 擴展函數
 */
fun <T> Flow<T>.asResult(): Flow<Result<T>> {
    return this
        .map<T, Result<T>> { value -> Result.Success(value) }
        .catch { exception -> emit(Result.Error(exception)) }
}

/**
 * 簡化 Flow 收集的擴展函數
 */
fun <T> Flow<T>.collectIn(
    scope: CoroutineScope,
    onEach: suspend (T) -> Unit,
    onError: suspend (Throwable) -> Unit = {}
) {
    this
        .onEach { onEach(it) }
        .catch { onError(it) }
        .launchIn(scope)
}

/**
 * Context 擴展函數
 */
fun Context.dpToPx(dp: Float): Float {
    return dp * resources.displayMetrics.density
}

fun Context.pxToDp(px: Float): Float {
    return px / resources.displayMetrics.density
}

/**
 * 集合擴展函數
 */
fun <T> List<T>.takeIfNotEmpty(): List<T>? {
    return if (isNotEmpty()) this else null
}

fun <K, V> Map<K, V>.getOrThrow(key: K, message: String = "Key not found: $key"): V {
    return this[key] ?: throw NoSuchElementException(message)
}