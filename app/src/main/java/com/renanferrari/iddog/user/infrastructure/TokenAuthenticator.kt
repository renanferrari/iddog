package com.renanferrari.iddog.user.infrastructure

import com.renanferrari.iddog.user.model.UserRepository
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(private val userRepository: UserRepository) : Authenticator {
  // TODO: Implement this
  override fun authenticate(route: Route?, response: Response): Request? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
