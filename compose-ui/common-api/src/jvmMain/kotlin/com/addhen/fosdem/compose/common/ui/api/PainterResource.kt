package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toAwtImage
import androidx.compose.ui.graphics.toPainter

@Composable
actual fun painterResource(imageResource: ImageResource): Painter {
  return return imageResource.image.toAwtImage().toPainter()
}
