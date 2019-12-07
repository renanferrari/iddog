package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.common.HttpError
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
      try {
        val response = api.signUp(SignUpRequest(email))
        if (response.isSuccessful) {
          val user = response.body()?.user
          repository.setUser(user)
          if (user != null) {
            Result.Success(user)
          } else {
            Result.Failure(SignUpError("User was null!"))
          }
        } else {
          Result.Failure(HttpError(response.code()))
        }
      } catch (e: Throwable) {
        return Result.Failure(e)
      }
    } else {
      Result.Failure(InvalidEmailError(email))
    }
  }

  class InvalidEmailError(email: String) : Throwable("Invalid email: $email")
  class SignUpError(message: String) : Throwable(message)
}
