package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.File
import org.jetbrains.skia.Image

actual class ImageResource(
  private val file: File
) {

  val image: ImageBitmap = file.inputStream().use { it.readBytes().toImageBitmap() }

  private fun ByteArray.toImageBitmap(): ImageBitmap =
    Image.makeFromEncoded(this).toComposeImageBitmap()
}
