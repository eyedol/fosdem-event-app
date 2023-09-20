package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun painterResource(imageResource: ImageResource): Painter
