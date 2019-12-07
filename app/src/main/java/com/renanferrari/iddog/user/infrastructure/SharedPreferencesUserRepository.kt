package com.renanferrari.iddog.user.infrastructure

import android.content.SharedPreferences
import androidx.core.content.edit
import com.renanferrari.iddog.user.model.User
import com.renanferrari.iddog.user.model.UserRepository

class SharedPreferencesUserRepository(
  private val sharedPreferences: SharedPreferences
) : UserRepository {
  companion object {
    const val KEY_EMAIL = "KEY_EMAIL"
    const val KEY_TOKEN = "KEY_TOKEN"
  }

  override fun setUser(user: User?) {
    sharedPreferences.edit {
      putString(KEY_EMAIL, user?.email)
      putString(KEY_TOKEN, user?.token)
    }
  }

  override fun getUser(): User? {
    val email = sharedPreferences.getString(KEY_EMAIL, null)
    val token = sharedPreferences.getString(KEY_TOKEN, null)
    return if (!email.isNullOrBlank() && !token.isNullOrBlank()) {
      User(email, token)
    } else {
      null
    }
  }
}