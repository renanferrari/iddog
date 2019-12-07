package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.user.model.UserRepository

class SignOut(private val repository: UserRepository) {
  fun execute() {
    repository.setUser(null)
  }
}
