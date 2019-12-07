package com.renanferrari.iddog.common.utils

public class Result<out T> constructor(
  private val value: Any?
) {
  public val isSuccess: Boolean get() = value !is Failure
  public val isFailure: Boolean get() = value is Failure

  public fun getOrNull(): T? =
      when {
        isFailure -> null
        else -> value as T
      }

  public fun exceptionOrNull(): Throwable? =
      when (value) {
        is Failure -> value.exception
        else -> null
      }

  public override fun toString(): String =
      when (value) {
        is Failure -> value.toString()
        else -> "Success($value)"
      }

  public companion object {
    public fun <T> success(value: T): Result<T> =
        Result(value)
    public fun <T> failure(exception: Throwable): Result<T> =
        Result(
            Failure(exception)
        )
  }

  internal class Failure(val exception: Throwable) {
    override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
    override fun hashCode(): Int = exception.hashCode()
    override fun toString(): String = "Failure($exception)"
  }
}