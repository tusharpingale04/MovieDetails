package com.tushar.mdetails.utils

import android.widget.ImageView
import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import coil.api.load
import com.tushar.mdetails.BuildConfig
import com.tushar.mdetails.R

@BindingAdapter("loadSmallImage")
fun loadSmallImage(imageView: ImageView,url: String?) {
    url?.let {
        imageView.load(BuildConfig.SMALL_IMAGE_URL+it) {
            placeholder(R.drawable.dummy_image)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("loadLargeImage")
fun loadLargeImage(imageView: ImageView,url: String?) {
    url?.let {
        imageView.load(BuildConfig.LARGE_IMAGE_URL+it) {
            placeholder(R.drawable.dummy_image)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("setRating")
fun setRating(ratingBar: RatingBar, vote: Double) {
   ratingBar.rating = (vote / 2).toFloat()
}