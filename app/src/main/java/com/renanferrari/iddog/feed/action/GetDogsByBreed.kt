package com.renanferrari.iddog.feed.action

import com.renanferrari.iddog.feed.model.DogsApi
import com.renanferrari.iddog.common.Result
import com.renanferrari.iddog.feed.model.Dog
import com.renanferrari.iddog.feed.model.Dog.Breed
import java.util.Locale.ROOT

class GetDogsByBreed(
  private val api: DogsApi
) {
  suspend fun execute(breed: Breed): Result<List<Dog>> {
    return api.feed(breed.name.toLowerCase(ROOT)).let { response ->
      if (response.isSuccessful) {
        Result.success((response.body()?.list.orEmpty()).map { Dog(it) })
      } else {
        Result.failure(HttpError(response.code()))
      }
    }
  }

  class HttpError(code: Int) : Error("HTTP error: $code")
}