/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addhen.fosdem.base.databinding

import android.text.TextUtils
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextSwitcher
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.addhen.fosdem.base.R

@BindingAdapter(value = ["buttonDrawableRes"])
fun setButtonDrawableRes(button: ImageButton, @DrawableRes drawableResId: Int) {
    button.setImageDrawable(ContextCompat.getDrawable(button.context, drawableResId))
}

@BindingAdapter(value = ["photoImageUrl"])
fun setPhotoImageUrl(imageView: ImageView, imageUrl: String?) {
    setImageUrl(imageView, imageUrl, R.color.grey200)
}

@BindingAdapter(value = ["photoImageUrl", "photoImageSize"])
fun setPhotoImageUrlWithSize(imageView: ImageView, imageUrl: String?, sizeInDimen: Float) {
    if (TextUtils.isEmpty(imageUrl)) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, R.color.grey200))
        return
    }
    // val size = Math.round(sizeInDimen)
    /*GlideApp.with(imageView.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(size, size)
        .centerCrop()
        .placeholder(R.color.grey200)
        .error(R.color.grey200)
        .transform(CircleCrop())
        .into(imageView)*/
}

@BindingAdapter(value = ["currentText"])
fun setCurrentText(view: TextSwitcher, text: String) {
    view.setCurrentText(text)
}

@BindingAdapter(value = ["coverFadeBackground"])
fun setCoverFadeBackground(view: View, @ColorRes colorResId: Int) {
    view.setBackgroundResource(colorResId)
}

private fun setImageUrlWithSize(
    imageView: ImageView,
    imageUrl: String?,
    sizeInDimen: Float,
    placeholderResId: Int
) {
    if (imageUrl.isNullOrEmpty()) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, placeholderResId))
        return
    }
    imageView.background = ContextCompat.getDrawable(
        imageView.context, R.drawable.shape_circle_border_grey200
    )
    // val size = Math.round(sizeInDimen)
    /*GlideApp.with(imageView.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .override(size, size)
        .centerCrop()
        .placeholder(placeholderResId)
        .error(placeholderResId)
        .transform(CircleCrop())
        .into(imageView)*/
}

private fun setImageUrl(
    imageView: ImageView,
    imageUrl: String?,
    @DrawableRes placeholderResId: Int
) {
    if (imageUrl.isNullOrEmpty()) {
        imageView.setImageDrawable(ContextCompat.getDrawable(imageView.context, placeholderResId))
        return
    }
    /*GlideApp.with(imageView.context)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholderResId)
        .error(placeholderResId)
        .into(imageView)*/
}
