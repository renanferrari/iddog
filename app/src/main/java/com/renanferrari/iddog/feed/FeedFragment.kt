package com.renanferrari.iddog.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.renanferrari.iddog.R
import com.renanferrari.iddog.common.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_feed.recycler_view

class FeedFragment : Fragment() {

  companion object {
    const val CATEGORY_KEY = "CATEGORY_KEY"

    fun newInstance(category: String): FeedFragment {
      return FeedFragment().apply {
        arguments = Bundle().apply {
          putString(CATEGORY_KEY, category)
        }
      }
    }
  }

  private lateinit var category: String

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    requireArguments().let {
      category = it.getString(CATEGORY_KEY)!!
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_feed, container)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    val spanCount = 2

    val layoutManager = GridLayoutManager(context, spanCount)
    recycler_view.layoutManager = layoutManager

    val itemSpacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
    recycler_view.addItemDecoration(GridSpacingItemDecoration(spanCount, itemSpacing))

    val adapter = FeedAdapter { dog, imageView ->
      ImageActivity.open(requireActivity(), imageView, dog.imageUrl)
    }
    recycler_view.adapter = adapter

    val dogs = getData(category).map { Dog(it) }
    adapter.submitList(dogs)
  }

  private fun getData(category: String): List<String> {
    return when (category) {
      "husky" -> DummyData.HUSKY
      "labrador" -> DummyData.LABRADOR
      "hound" -> DummyData.HOUND
      "pug" -> DummyData.PUG
      else -> emptyList()
    }
  }
}
