package io.github.aris0x145.kimerai.core.common.result

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/**
 * 提供 Kotlin 標準庫 Result 的擴展函數。
 * 這些擴展函數為標準 Result 類增加了額外的功能，
 * 使其更適合在應用中使用。
 */

/**
 * 將 Result 轉換為 Arrow Either
 */
fun <T> Result<T>.toEither(): Either<Throwable, T> = fold(
    onSuccess = { it.right() },
    onFailure = { it.left() }
)

/**
 * 從 Arrow Either 創建 Result
 */
fun <T> Either<Throwable, T>.toResult(): Result<T> = fold(
    { Result.failure(it) },
    { Result.success(it) }
)

/**
 * 將可空類型轉換為 Result，null 值將變為錯誤
 */
fun <T : Any> T?.toResult(): Result<T> = this?.let { Result.success(it) }
    ?: Result.failure(NullPointerException("Value is null"))

/**
 * 從結果中獲取值，如果是錯誤則返回預設值
 */
fun <T> Result<T>.getOrDefault(defaultValue: T): T = fold(
    onSuccess = { it },
    onFailure = { defaultValue }
)

/**
 * 映射錯誤到新的錯誤
 */
inline fun <T> Result<T>.mapError(transform: (Throwable) -> Throwable): Result<T> = fold(
    onSuccess = { Result.success(it) },
    onFailure = { Result.failure(transform(it)) }
)

/**
 * 將成功結果轉換為新的 Result (類似於 flatMap)
 */
inline fun <T, R> Result<T>.andThen(transform: (T) -> Result<R>): Result<R> = fold(
    onSuccess = { transform(it) },
    onFailure = { Result.failure(it) }
)

/**
 * 將 suspend 函數的調用結果包裝為 Result
 */
suspend fun <T> runCatchingSuspend(block: suspend () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}

/**
 * 異步執行 Result 變換，保持錯誤處理
 */
suspend fun <T, R> Result<T>.mapSuspend(transform: suspend (T) -> R): Result<R> = fold(
    onSuccess = { 
        try {
            Result.success(transform(it))
        } catch (e: Throwable) {
            Result.failure(e)
        }
    },
    onFailure = { Result.failure(it) }
)
