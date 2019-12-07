package com.renanferrari.iddog.feed.model

data class Dog(
  val imageUrl: String
) {
  enum class Breed {
    HUSKY,
    LABRADOR,
    HOUND,
    PUG
  }
}