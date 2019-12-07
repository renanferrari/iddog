package com.renanferrari.iddog.feed.action

import com.renanferrari.iddog.common.HttpError
import com.renanferrari.iddog.common.Result
import com.renanferrari.iddog.feed.model.Dog
import com.renanferrari.iddog.feed.model.Dog.Breed
import com.renanferrari.iddog.feed.model.DogsApi
import java.util.Locale.ROOT

class GetDogsByBreed(
  private val api: DogsApi
) {
  suspend fun execute(breed: Breed): Result<List<Dog>> {
    return try {
      val response = api.feed(breed.name.toLowerCase(ROOT))
      if (response.isSuccessful) {
        Result.Success((response.body()?.list.orEmpty()).map { Dog(it) })
      } else {
        Result.Failure(HttpError(response.code()))
      }
    } catch (e: Throwable) {
      Result.Failure(e)
    }
  }
}