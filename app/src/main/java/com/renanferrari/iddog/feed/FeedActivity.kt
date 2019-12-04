package com.renanferrari.iddog.feed

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.renanferrari.iddog.R
import kotlinx.android.synthetic.main.activity_feed.tabs
import kotlinx.android.synthetic.main.activity_feed.view_pager

class FeedActivity : AppCompatActivity() {

  companion object {
    const val HUSKY_PAGE_INDEX = 0
    const val LABRADOR_PAGE_INDEX = 1
    const val HOUND_PAGE_INDEX = 2
    const val PUG_PAGE_INDEX = 3
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_feed)

    view_pager.adapter = FeedPagerAdapter(supportFragmentManager, lifecycle)

    TabLayoutMediator(tabs, view_pager) { tab, position ->
      tab.text = getTabTitle(position)
    }.attach()
  }

  private fun getTabTitle(position: Int): String? {
    return when (position) {
      HUSKY_PAGE_INDEX -> getString(R.string.label_husky)
      LABRADOR_PAGE_INDEX -> getString(R.string.label_labrador)
      HOUND_PAGE_INDEX -> getString(R.string.label_hound)
      PUG_PAGE_INDEX -> getString(R.string.label_pug)
      else -> null
    }
  }
}
