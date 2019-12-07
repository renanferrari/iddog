package com.renanferrari.iddog.user.model

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApi {
  @POST("signup")
  @Headers("Content-Type: application/json")
  suspend fun signUp(@Body authRequest: SignUpRequest): Response<SignUpResponse>

  data class SignUpRequest(val email: String)
  data class SignUpResponse(val user: User)
}