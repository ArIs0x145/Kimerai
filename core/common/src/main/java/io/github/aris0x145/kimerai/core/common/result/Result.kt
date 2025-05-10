package io.github.aris0x145.kimerai.core.common.result

import arrow.core.Either
import arrow.core.left
import arrow.core.right

/**
 * 表示操作的結果，可以是成功 [Success] 或失敗 [Error]
 * 此類是對 Arrow Either 的包裝，提供更直觀的 API
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Throwable) : Result<Nothing>()

    /**
     * 如果結果是 [Success]，則執行指定的操作
     */
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    /**
     * 如果結果是 [Error]，則執行指定的操作
     */
    inline fun onError(action: (Throwable) -> Unit): Result<T> {
        if (this is Error) action(exception)
        return this
    }

    /**
     * 將成功結果映射到新的值
     */
    inline fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> Error(exception)
        }
    }

    /**
     * 映射錯誤到新的錯誤
     */
    inline fun mapError(transform: (Throwable) -> Throwable): Result<T> {
        return when (this) {
            is Success -> this
            is Error -> Error(transform(exception))
        }
    }

    /**
     * 將成功結果轉換為新的 Result
     */
    inline fun <R> flatMap(transform: (T) -> Result<R>): Result<R> {
        return when (this) {
            is Success -> transform(data)
            is Error -> Error(exception)
        }
    }

    /**
     * 從結果中獲取值，如果是錯誤則返回 null
     */
    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    /**
     * 從結果中獲取值，如果是錯誤則返回預設值
     */
    fun getOrDefault(defaultValue: @UnsafeVariance T): T = when (this) {
        is Success -> data
        is Error -> defaultValue
    }

    /**
     * 從結果中獲取值，如果是錯誤則拋出該錯誤
     */
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw exception
    }

    /**
     * 將 Result 轉換為 Arrow Either
     */
    fun toEither(): Either<Throwable, T> = when (this) {
        is Success -> data.right()
        is Error -> exception.left()
    }

    companion object {
        /**
         * 捕獲可能拋出異常的代碼塊並將其包裝為 Result
         */
        inline fun <T> runCatching(block: () -> T): Result<T> {
            return try {
                Success(block())
            } catch (e: Throwable) {
                Error(e)
            }
        }

        /**
         * 從 Arrow Either 創建 Result
         */
        fun <T> fromEither(either: Either<Throwable, T>): Result<T> = either.fold(
            ifLeft = { Error(it) },
            ifRight = { Success(it) }
        )
    }
}

/**
 * 將可空類型轉換為 Result，null 值將變為錯誤
 */
fun <T : Any> T?.toResult(): Result<T> = this?.let { Result.Success(it) }
    ?: Result.Error(NullPointerException("Value is null"))

/**
 * 將 suspend 函數的調用結果包裝為 Result
 */
suspend fun <T> runCatchingSuspend(block: suspend () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Error(e)
    }
}
