package com.example.customgallery.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingUtils {
    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if(imageUrl !=null) {
                Glide.with(view.context)
                    .load(imageUrl)
                    .into(view)
            }
        }
    }
}