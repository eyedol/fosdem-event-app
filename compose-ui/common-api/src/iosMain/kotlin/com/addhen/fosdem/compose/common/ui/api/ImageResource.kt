// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

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
