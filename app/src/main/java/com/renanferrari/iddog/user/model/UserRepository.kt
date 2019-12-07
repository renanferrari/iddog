package com.renanferrari.iddog.user.model

interface UserRepository {
  fun setUser(user: User?)
  fun getUser(): User?
}