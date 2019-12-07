package com.renanferrari.iddog.user.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.observe
import com.renanferrari.iddog.R
import com.renanferrari.iddog.feed.FeedActivity
import kotlinx.android.synthetic.main.activity_signup.button_view
import kotlinx.android.synthetic.main.activity_signup.email_edit_text
import kotlinx.android.synthetic.main.activity_signup.email_input
import kotlinx.android.synthetic.main.activity_signup.progress_bar
import kotlinx.android.synthetic.main.activity_signup.title_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

  private val viewModel: SignUpViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_signup)

    button_view.setOnClickListener { signUp(email_edit_text.text.toString()) }

    viewModel.state.observe(this) { state ->
      if (state.user != null) {
        FeedActivity.open(this)
        finish()
      } else {
        email_input.isEnabled = !state.loading
        button_view.isEnabled = !state.loading
        title_view.isVisible = !state.loading
        progress_bar.isVisible = state.loading

        if (state.error.isNullOrBlank()) {
          email_input.isErrorEnabled = false
        } else {
          email_input.error = state.error
        }
      }
    }
  }

  private fun signUp(email: String) = viewModel.setEmail(email)
}
