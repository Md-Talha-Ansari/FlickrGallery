package com.hind.flickrgallery.ui.adapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object BindingAdapters {

    /**
     * Binding adapter for ImageView to
     */
    @JvmStatic
    @BindingAdapter(value = ["imageUrl", "placeholder"], requireAll = false)
    fun setImageUrl(view: ImageView, url: String?, placeHolder: Drawable?) {
        if (url == null) {
            view.setImageDrawable(placeHolder);
        } else {
            Glide
                .with(view.context)
                .load(url)
                .apply {
                    when(view.scaleType){
                        ImageView.ScaleType.FIT_CENTER -> fitCenter()
                        ImageView.ScaleType.CENTER_CROP -> centerCrop()
                        ImageView.ScaleType.CENTER_INSIDE -> centerInside()
                        else -> fitCenter()
                    }
                }
                .error(placeHolder)
                .into(view)
        }
    }

}