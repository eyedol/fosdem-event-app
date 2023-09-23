// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.File

actual class ImageResource(
  private val file: File,
) {

  val image: ImageBitmap = file.inputStream().use { it.readBytes().toImageBitmap() }

  private fun ByteArray.toImageBitmap(): ImageBitmap =
    Image.makeFromEncoded(this).toComposeImageBitmap()
}

@Composable
actual fun AppImage.asImageResource() = remember(this) {
  val file = when (this) {
    AppImage.FosdemLogo -> File("path_to_fosdem_logo")
  }
  ImageResource(file)
}
