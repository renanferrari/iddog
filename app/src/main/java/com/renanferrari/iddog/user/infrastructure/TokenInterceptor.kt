package com.renanferrari.iddog.user.infrastructure

import com.renanferrari.iddog.user.model.UserRepository
import okhttp3.Interceptor

class TokenInterceptor(private val repository: UserRepository) : Interceptor {
  override fun intercept(chain: Interceptor.Chain) = with(chain.request().newBuilder()) {
    repository.getUser()?.token?.let { addHeader("Authorization", it) }
    chain.proceed(build())
  }
}
