package com.renanferrari.iddog.feed.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.renanferrari.iddog.R
import com.renanferrari.iddog.feed.model.Dog.Breed
import com.renanferrari.iddog.user.ui.SignUpActivity
import kotlinx.android.synthetic.main.activity_feed.tabs
import kotlinx.android.synthetic.main.activity_feed.toolbar
import kotlinx.android.synthetic.main.activity_feed.view_pager
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedActivity : AppCompatActivity() {

  private val viewModel: FeedViewModel by viewModel()

  companion object {
    fun open(activity: Activity) {
      activity.startActivity(Intent(activity, FeedActivity::class.java))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_feed)

    toolbar.inflateMenu(R.menu.menu_feed)
    toolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.action_sign_out -> viewModel.signOut().let {
          SignUpActivity.open(this)
          finish()
          true
        }
        else -> false
      }
    }

    view_pager.adapter = FeedPagerAdapter(supportFragmentManager, lifecycle)

    TabLayoutMediator(tabs, view_pager) { tab, position ->
      tab.text = Breed.values()[position].name
    }.attach()
  }
}
