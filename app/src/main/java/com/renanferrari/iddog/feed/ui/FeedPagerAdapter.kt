package com.renanferrari.iddog.feed.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.renanferrari.iddog.feed.model.Dog.Breed

class FeedPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

  override fun getItemCount() = Breed.values().size

  override fun createFragment(position: Int): Fragment =
      FeedFragment.newInstance(Breed.values()[position])
}
