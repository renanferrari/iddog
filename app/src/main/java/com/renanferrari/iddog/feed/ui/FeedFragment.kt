package com.renanferrari.iddog.feed.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.renanferrari.iddog.R
import com.renanferrari.iddog.common.GridSpacingItemDecoration
import com.renanferrari.iddog.feed.model.Dog.Breed
import kotlinx.android.synthetic.main.fragment_feed.error_view
import kotlinx.android.synthetic.main.fragment_feed.progress_bar
import kotlinx.android.synthetic.main.fragment_feed.recycler_view
import org.koin.androidx.viewmodel.ext.android.viewModel

class FeedFragment : Fragment() {

  private val viewModel: FeedViewModel by viewModel()

  companion object {
    const val KEY_BREED = "KEY_BREED"

    fun newInstance(breed: Breed): FeedFragment {
      return FeedFragment().apply {
        arguments = Bundle().apply {
          putString(KEY_BREED, breed.name)
        }
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_feed, container)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    requireArguments().let {
      viewModel.setBreed(Breed.valueOf(it.getString(KEY_BREED)!!))
    }

    val spanCount = 2

    recycler_view.layoutManager = GridLayoutManager(context, spanCount)

    val itemSpacing = resources.getDimensionPixelSize(R.dimen.item_spacing)
    recycler_view.addItemDecoration(GridSpacingItemDecoration(spanCount, itemSpacing))

    val adapter = FeedAdapter { dog, imageView ->
      ImageActivity.open(requireActivity(), imageView, dog.imageUrl)
    }
    recycler_view.adapter = adapter

    viewModel.state.observe(this) { state ->
      state.dogs.let { adapter.submitList(it) }
      progress_bar.isVisible = state.loading
      recycler_view.isVisible = !state.loading && state.error.isNullOrBlank()
      error_view.text = state.error
    }
  }
}
