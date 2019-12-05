package com.renanferrari.iddog.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renanferrari.iddog.R
import com.renanferrari.iddog.feed.FeedActivity
import kotlinx.android.synthetic.main.activity_login.button_view

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    button_view.setOnClickListener { FeedActivity.open(this) }
  }
}
