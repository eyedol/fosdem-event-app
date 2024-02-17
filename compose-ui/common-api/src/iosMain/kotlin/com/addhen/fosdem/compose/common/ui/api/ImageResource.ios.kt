// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.Foundation.NSBundle
import platform.UIKit.UIImage

actual class ImageResource(
  val assetImageName: String,
  val bundle: NSBundle = NSBundle.mainBundle,
) {
  fun toUIImage(): UIImage? {
    return UIImage.imageNamed(
      name = assetImageName,
      inBundle = bundle,
      compatibleWithTraitCollection = null,
    )
  }
}

@Composable
actual fun AppImage.asImageResource() = remember(this) {
  val assetImageName = when (this) {
    AppImage.FosdemLogo -> "path_to_fosdem_logo"
    AppImage.InstagramLogo -> TODO()
    AppImage.MastadonLogo -> TODO()
    AppImage.XLogo -> TODO()
    AppImage.FacebookLogo -> TODO()
    AppImage.AboutBanner -> TODO()
  }
  ImageResource(assetImageName)
}
