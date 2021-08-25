package com.cactus.marvelcomics.common

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewProperties(items: List<T>) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).setData(items)
    }
}

@BindingAdapter("deleteItem")
fun <T> RecyclerView.setDeleteItem(positions: Int?) {
    if (this.adapter is BindableAdapter<*>) {
        (this.adapter as BindableAdapter<T>).deleteItem(positions)
    }
}

@BindingAdapter("changedPositions")
fun <T> setDataChanged(recyclerView: RecyclerView, positions: Set<Int>) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).changedPositions(positions)
    }
}

@BindingAdapter("requestFocus")
fun View.requestFocus(requestFocus: Boolean) {
    this.isFocusableInTouchMode = requestFocus
}

@BindingAdapter(value = ["setImageUrl"])
fun ImageView.bindImageUrl(url: String?) {
    if (url != null && url.isNotBlank()) {
        Picasso.get()
            .load(url)
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    this@bindImageUrl.setImageBitmap(bitmap)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    this@bindImageUrl.setImageDrawable(errorDrawable)
                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    this@bindImageUrl.setImageDrawable(placeHolderDrawable)
                }
            })
    }
}
