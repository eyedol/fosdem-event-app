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
  val fileName = when (this) {
    AppImage.FosdemLogo -> "fosdem_logo.webp"
    AppImage.InstagramLogo -> "fosdem_logo.webp"
    AppImage.MastadonLogo -> "fosdem_logo.webp"
    AppImage.XLogo -> "fosdem_logo.webp"
    AppImage.FacebookLogo -> "fosdem_logo.webp"
    AppImage.AboutBanner -> "fosdem_logo.webp"
  }
  ImageResource(this::class.java.getResourceAsStream("/$fileName")!!)
}
