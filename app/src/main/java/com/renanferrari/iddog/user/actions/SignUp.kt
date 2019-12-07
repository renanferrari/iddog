package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.common.infrastructure.IDDogApi
import com.renanferrari.iddog.common.infrastructure.IDDogApi.SignUpRequest
import com.renanferrari.iddog.common.utils.Result
import com.renanferrari.iddog.common.utils.isValidEmail
import com.renanferrari.iddog.user.model.User
import com.renanferrari.iddog.user.model.UserRepository

class SignUp(
  private val api: IDDogApi,
  private val userRepository: UserRepository
) {
  suspend fun execute(input: String): Result<User> {
    return if (input.isValidEmail()) {
      api.signUp(SignUpRequest(input)).let { response ->
        if (response.isSuccessful) {
          val user = response.body()?.user
          userRepository.setUser(user)
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
      Result.failure(InvalidEmailError(input))
    }
  }

  class InvalidEmailError(email: String) : Error("Invalid email: $email")
  class InvalidResponseError : Error("Invalid response")
  class HttpError(code: Int) : Error("HTTP error: $code")
}
