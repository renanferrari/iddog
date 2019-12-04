package com.renanferrari.iddog.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.renanferrari.iddog.R.layout
import com.renanferrari.iddog.feed.FeedActivity
import kotlinx.android.synthetic.main.activity_login.button_view

class LoginActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_login)

    button_view.setOnClickListener {
      startActivity(Intent(this, FeedActivity::class.java))
    }
  }
}
