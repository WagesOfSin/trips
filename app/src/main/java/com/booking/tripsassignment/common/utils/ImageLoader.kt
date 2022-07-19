package com.booking.tripsassignment.common.utils

import android.widget.ImageView
import com.booking.tripsassignment.R
import com.bumptech.glide.Glide

/**
 * Helper to load images using Glide.
 */
object ImageLoader {

    /**
     * Common method to load and cache images using Glide.
     */
    fun loadImage(view: ImageView, image: String) {
        Glide.with(view)
            .load(image)
            .centerCrop()
            .error(R.drawable.no_img)
            .into(view)
    }
}