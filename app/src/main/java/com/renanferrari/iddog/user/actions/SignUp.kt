package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.common.Result
import com.renanferrari.iddog.common.extensions.isValidEmail
import com.renanferrari.iddog.user.model.User
import com.renanferrari.iddog.user.model.UserApi
import com.renanferrari.iddog.user.model.UserApi.SignUpRequest
import com.renanferrari.iddog.user.model.UserRepository

class SignUp(
  private val api: UserApi,
  private val repository: UserRepository
) {
  suspend fun execute(email: String): Result<User> {
    return if (email.isValidEmail()) {
      api.signUp(SignUpRequest(email)).let { response ->
        if (response.isSuccessful) {
          val user = response.body()?.user
          repository.setUser(user)
          if (user != null) {
            Result.success(user)
          } else {
            Result.failure(InvalidResponseError())
          }
        } else {
          Result.failure(HttpError(response.code()))
        }
      }
    } else {
      Result.failure(InvalidEmailError(email))
    }
  }

  class InvalidEmailError(email: String) : Error("Invalid email: $email")
  class InvalidResponseError : Error("Invalid response")
  class HttpError(code: Int) : Error("HTTP error: $code")
}
