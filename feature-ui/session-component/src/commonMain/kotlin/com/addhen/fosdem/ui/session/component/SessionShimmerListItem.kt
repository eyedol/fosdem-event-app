// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

const val SessionShimmerListItemTestTag = "SessionShimmerListItemList"
const val SessionShimmerListTestTag = "SessionShimmerListList"

@Composable
fun SessionShimmerList(
  contentPadding: PaddingValues,
  modifier: Modifier = Modifier,
) {
  LazyColumn(
    modifier = modifier.testTag(SessionShimmerListTestTag),
    contentPadding = contentPadding,
  ) {
    items(10) {
      Row(modifier = Modifier.padding(start = 16.dp, top = 10.dp, end = 16.dp)) {
        Spacer(modifier = Modifier.size(58.dp))
        SessionShimmerListItem()
      }
    }
  }
}

@Composable
private fun SessionShimmerListItem(modifier: Modifier = Modifier) {
  Column(
    modifier = modifier.testTag(SessionShimmerListItemTestTag),
  ) {
    Spacer(modifier = Modifier.size(5.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      //  Shimmer effect on top
      Box(
        modifier = Modifier
          .height(50.dp)
          .fillMaxWidth()
          .shimmerBackground(RoundedCornerShape(4.dp)),

      )
    }
    Spacer(modifier = Modifier.size(12.dp))
    Row {
      //  Shimmer effect on bottom(left)
      Box(
        modifier = Modifier
          .height(40.dp)
          .width(40.dp)
          .clip(RoundedCornerShape(12.dp))
          .shimmerBackground(RoundedCornerShape(4.dp))
          .background(Color.LightGray),
      )
      Spacer(modifier = Modifier.size(10.dp))
      //  Shimmer effect on bottom(right)
      Box(
        modifier = Modifier
          .height(32.dp)
          .width(80.dp)
          .shimmerBackground(RoundedCornerShape(4.dp)),
      )
    }
    Spacer(modifier = Modifier.size(15.dp))
    HorizontalDivider()
  }
}

// Credits: https://touchlab.co/loading-shimmer-in-compose
fun Modifier.shimmerBackground(shape: Shape = RectangleShape): Modifier = composed {
  val transition = rememberInfiniteTransition()

  val translateAnimation by transition.animateFloat(
    initialValue = 0f,
    targetValue = 400f,
    animationSpec = infiniteRepeatable(
      tween(durationMillis = 1500, easing = LinearOutSlowInEasing),
      RepeatMode.Restart,
    ),
  )
  val shimmerColors = listOf(
    Color.LightGray.copy(alpha = 0.9f),
    Color.LightGray.copy(alpha = 0.4f),
  )
  val brush = Brush.linearGradient(
    colors = shimmerColors,
    start = Offset(translateAnimation, translateAnimation),
    end = Offset(translateAnimation + 100f, translateAnimation + 100f),
    tileMode = TileMode.Mirror,
  )
  return@composed this.then(background(brush, shape))
}
