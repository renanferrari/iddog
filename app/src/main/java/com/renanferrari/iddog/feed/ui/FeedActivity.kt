package com.renanferrari.iddog.feed.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.renanferrari.iddog.R
import com.renanferrari.iddog.feed.model.Dog.Breed
import kotlinx.android.synthetic.main.activity_feed.tabs
import kotlinx.android.synthetic.main.activity_feed.view_pager

class FeedActivity : AppCompatActivity() {

  companion object {
    fun open(activity: Activity) {
      activity.startActivity(Intent(activity, FeedActivity::class.java))
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_feed)

    view_pager.adapter = FeedPagerAdapter(supportFragmentManager, lifecycle)

    TabLayoutMediator(tabs, view_pager) { tab, position ->
      tab.text = Breed.values()[position].name
    }.attach()
  }
}
