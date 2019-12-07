package com.renanferrari.iddog.feed.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface DogsApi {
  @GET("feed")
  @Headers("Content-Type: application/json")
  suspend fun feed(@Query("category") category: String): Response<FeedResponse>

  data class FeedResponse(val category: String, val list: List<String>)
}