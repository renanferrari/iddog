package com.renanferrari.iddog.feed.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.renanferrari.iddog.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_image.image_view

class ImageActivity : AppCompatActivity() {

  companion object {
    const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

    fun open(activity: Activity, sharedImageView: ImageView, url: String) {
      activity.startActivity(
          Intent(activity, ImageActivity::class.java).apply {
            putExtra(KEY_IMAGE_URL, url)
          },
          ActivityOptionsCompat.makeSceneTransitionAnimation(
              activity,
              sharedImageView,
              activity.getString(R.string.transition_image)
          ).toBundle()
      )
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image)

    intent.getStringExtra(KEY_IMAGE_URL).let { url ->
      Picasso.get()
          .load(url)
          .placeholder(R.drawable.layer_list_placeholder)
          .into(image_view)
    }
  }
}
