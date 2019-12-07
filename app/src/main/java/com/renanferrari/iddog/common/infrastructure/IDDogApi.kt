package com.renanferrari.iddog.common.infrastructure

import com.renanferrari.iddog.user.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface IDDogApi {
  companion object {
    const val BASE_URL = "https://api-iddog.idwall.co/"
  }

  @POST("signup")
  @Headers("Content-Type: application/json")
  suspend fun signUp(@Body authRequest: SignUpRequest): Response<SignUpResponse>

  @GET("feed")
  @Headers("Content-Type: application/json")
  suspend fun feed(@Query("category") category: String?): Response<FeedResponse>

  data class SignUpRequest(val email: String)
  data class SignUpResponse(val user: User)
  data class FeedResponse(val category: String, val list: List<String>)
}