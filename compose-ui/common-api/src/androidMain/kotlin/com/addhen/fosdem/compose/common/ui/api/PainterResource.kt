package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
actual fun painterResource(imageResource: ImageResource): Painter {
  return androidx.compose.ui.res.painterResource(id = imageResource.drawableResId)
}
