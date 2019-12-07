package com.renanferrari.iddog.user.actions

import com.renanferrari.iddog.user.model.UserRepository

class GetUser(private val userRepository: UserRepository) {
  fun execute() = userRepository.getUser()
}
