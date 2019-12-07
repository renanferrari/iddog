package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.user.model.UserRepository

class GetUser(private val repository: UserRepository) {
  fun execute() = repository.getUser()
}
