// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.compose.common.ui.api.painterResource

@Composable
fun MapContentBox(
  imageResource: ImageResource?,
  innerPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .padding(innerPadding),
    contentAlignment = Alignment.Center,
  ) {
    val minScale = 1f
    val maxScale = 3f
    var scale by remember { mutableStateOf(minScale) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, _ ->
      scale *= zoomChange
      offset += offsetChange
    }
    imageResource?.let {
      Image(
        painter = painterResource(it),
        contentDescription = "Floor map",
        modifier = modifier
          .transformable(state = state)
          .graphicsLayer(
            scaleX = scale.coerceIn(minScale, maxScale),
            scaleY = scale.coerceIn(minScale, maxScale),
            translationX = offset.x,
            translationY = offset.y,
          )
          .fillMaxSize()
          .padding(16.dp),
      )
    }
  }
}
