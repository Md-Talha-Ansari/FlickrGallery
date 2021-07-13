package com.hind.flickrgallery.ui.adapters

import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    private const val TAG = "BindingAdapters"

    /**
     * Binding adapter for ImageView to load images from url.
     * @param view ImageView to load image to.
     * @param url URL to load image from.
     * @param placeHolder Image to show if cannot download image.
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

    /**
     * Binding adapter to convert server date time to user readable date time.
     * @param view TextView to set date to.
     * @param dateString Server formatted date.
     * @param fromFormat Server date format.
     * @param toFormat User readable date format.
     */
    @JvmStatic
    @BindingAdapter(value = ["rawDateText","fromFormat","toFormat"],requireAll = true)
    fun formatRawDateText(view:TextView,dateString:String?,fromFormat: String,toFormat: String){
        if(dateString != null){
            try {
                val fromFormatter = SimpleDateFormat(fromFormat,Locale.getDefault())
                val date = fromFormatter.parse(dateString)
                if (date != null){
                    val toFormatter = SimpleDateFormat(toFormat,Locale.getDefault())
                    view.text = toFormatter.format(date)
                }else{
                    view.text = dateString
                }
            }catch(ex:ParseException){
                Log.d(TAG,ex.localizedMessage)
                view.text = dateString
            }
        }
    }

}