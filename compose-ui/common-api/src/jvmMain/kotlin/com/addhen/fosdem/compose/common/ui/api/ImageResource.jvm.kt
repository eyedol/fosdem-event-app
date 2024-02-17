// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.InputStream
import org.jetbrains.skia.Image

actual class ImageResource(
  inputStream: InputStream,
) {

  val image: ImageBitmap = inputStream.use { it.readBytes().toImageBitmap() }

  private fun ByteArray.toImageBitmap(): ImageBitmap =
    Image.makeFromEncoded(this).toComposeImageBitmap()
}

@Composable
actual fun AppImage.asImageResource() = remember(this) {
  val inputStream = when (this) {
    AppImage.FosdemLogo -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
    AppImage.InstagramLogo -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
    AppImage.MastadonLogo -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
    AppImage.XLogo -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
    AppImage.FacebookLogo -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
    AppImage.AboutBanner -> this::class.java.getResourceAsStream("/fosdem_logo.webp")
  }
  ImageResource(inputStream!!)
}
