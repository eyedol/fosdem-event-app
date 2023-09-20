package com.addhen.fosdem.compose.common.ui.api

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
actual class ImageResource(
  @DrawableRes val drawableResId: Int
) : Parcelable
