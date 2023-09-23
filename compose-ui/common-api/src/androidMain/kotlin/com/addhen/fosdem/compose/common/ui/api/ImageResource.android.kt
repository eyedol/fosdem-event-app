// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.parcelize.Parcelize

@Parcelize
actual class ImageResource(
  @DrawableRes val drawableResId: Int,
) : Parcelable

@Composable
actual fun AppImage.asImageResource(): ImageResource = remember(this) {
  val resId = when (this) {
    AppImage.FosdemLogo -> R.drawable.fosdem_logo
  }
  ImageResource(resId)
}
