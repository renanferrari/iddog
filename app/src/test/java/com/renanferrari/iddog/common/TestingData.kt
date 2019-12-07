package com.renanferrari.iddog.common

import com.renanferrari.iddog.feed.model.Dog
import com.renanferrari.iddog.user.model.User
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

object TestingData {
  fun <T> makeSuccessResponse(result: T? = null) = Response.success(result)
  fun <T> makeErrorResponse(code: Int, result: String = ""): Response<T> =
      Response.error(code, result.toResponseBody("application/json".toMediaTypeOrNull()))

  const val INVALID_EMAIL = "invalid@email"
  const val VALID_EMAIL = "valid@email.com"
  val USER = User(VALID_EMAIL, "token")

  const val HUSKY_CATEGORY = "husky"
  val HUSKY_URL_LIST = listOf(
      "https://images.dog.ceo/breeds/husky/n02110185_10047.jpg",
      "https://images.dog.ceo/breeds/husky/n02110185_10116.jpg",
      "https://images.dog.ceo/breeds/husky/n02110185_10171.jpg",
      "https://images.dog.ceo/breeds/husky/n02110185_10175.jpg",
      "https://images.dog.ceo/breeds/husky/n02110185_10273.jpg"
  )
  val HUSKY_DOG_LIST = listOf(
      Dog("https://images.dog.ceo/breeds/husky/n02110185_10047.jpg"),
      Dog("https://images.dog.ceo/breeds/husky/n02110185_10116.jpg"),
      Dog("https://images.dog.ceo/breeds/husky/n02110185_10171.jpg"),
      Dog("https://images.dog.ceo/breeds/husky/n02110185_10175.jpg"),
      Dog("https://images.dog.ceo/breeds/husky/n02110185_10273.jpg")
  )
}