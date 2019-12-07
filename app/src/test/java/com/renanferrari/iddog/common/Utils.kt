package com.renanferrari.iddog.common

import com.renanferrari.iddog.user.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object Utils {
  const val INVALID_EMAIL = "invalid@email"
  const val VALID_EMAIL = "valid@email.com"
  val USER = User(VALID_EMAIL, "token")

  fun <T> makeSuccessResponse(result: T? = null) = Response.success(result)
  fun <T> makeErrorResponse(code: Int, result: String = ""): Response<T> =
      Response.error(code, result.toResponseBody("application/json".toMediaTypeOrNull()))
}