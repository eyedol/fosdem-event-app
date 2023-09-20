package com.addhen.fosdem.compose.common.ui.api

import platform.Foundation.NSBundle
import platform.UIKit.UIImage

actual class ImageResource(
  val assetImageName: String,
  val bundle: NSBundle = NSBundle.mainBundle
) {
  fun toUIImage(): UIImage? {
    return UIImage.imageNamed(
      name = assetImageName,
      inBundle = bundle,
      compatibleWithTraitCollection = null
    )
  }
}
