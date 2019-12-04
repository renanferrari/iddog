package com.renanferrari.iddog.feed

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.renanferrari.iddog.feed.FeedActivity.Companion.HOUND_PAGE_INDEX
import com.renanferrari.iddog.feed.FeedActivity.Companion.HUSKY_PAGE_INDEX
import com.renanferrari.iddog.feed.FeedActivity.Companion.LABRADOR_PAGE_INDEX
import com.renanferrari.iddog.feed.FeedActivity.Companion.PUG_PAGE_INDEX

class FeedPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

  private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
      HUSKY_PAGE_INDEX to { FeedFragment.newInstance("husky") },
      LABRADOR_PAGE_INDEX to { FeedFragment.newInstance("labrador") },
      HOUND_PAGE_INDEX to { FeedFragment.newInstance("hound") },
      PUG_PAGE_INDEX to { FeedFragment.newInstance("pug") }
  )

  override fun getItemCount() = tabFragmentsCreators.size

  override fun createFragment(position: Int): Fragment {
    return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
  }
}
