package com.renanferrari.iddog.common

sealed class Result<out T : Any> {
  data class Success<out T : Any>(val value: T) : Result<T>()
  data class Failure(val cause: Throwable) : Result<Nothing>()
}