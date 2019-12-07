package com.renanferrari.iddog.feed

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.renanferrari.iddog.R
import com.renanferrari.iddog.common.utils.inflate
import com.renanferrari.iddog.feed.FeedAdapter.ViewHolder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_dog.view.image_view

class FeedAdapter(
  private val onDogClicked: (dog: Dog, imageView: ImageView) -> Unit
) : ListAdapter<Dog, ViewHolder>(DogDiffCallback()) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(parent.context.inflate(R.layout.item_dog, parent))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val dog = getItem(position)

    Picasso.get()
        .load(dog.imageUrl)
        .placeholder(R.drawable.layer_list_placeholder)
        .into(holder.imageView)

    holder.itemView.setOnClickListener { onDogClicked(dog, holder.imageView) }
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageView: ImageView = itemView.image_view
  }
}

private class DogDiffCallback : DiffUtil.ItemCallback<Dog>() {
  override fun areItemsTheSame(oldItem: Dog, newItem: Dog): Boolean {
    return oldItem == newItem
  }

  override fun areContentsTheSame(oldItem: Dog, newItem: Dog): Boolean {
    return oldItem.imageUrl == newItem.imageUrl
  }
}