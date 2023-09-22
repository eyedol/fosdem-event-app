// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.theme.fosdem_pink
import kotlinx.collections.immutable.PersistentList

@Immutable
data class Tag(val title: String, val color: Color)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionHeader(
  tile: String,
  year: String,
  location: String,
  tags: PersistentList<Tag>,
  painter: Painter,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(Modifier.weight(1.5f)) {
      Text(
        text = buildAnnotatedString {
          withStyle(style = MaterialTheme.typography.displaySmall.toSpanStyle()) {
            append(tile)
          }
          withStyle(
            style = MaterialTheme.typography.displaySmall
              .toSpanStyle()
              .copy(color = fosdem_pink),
          ) {
            append("\u02BC$year")
          }
        },
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = location,
        style = MaterialTheme.typography.labelMedium,
      )
      Spacer(modifier = Modifier.height(16.dp))
      FlowRow(
        Modifier
          .wrapContentWidth()
          .wrapContentHeight(align = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 3,

      ) {
        for (tag in tags) {
          TagItem(tag)
        }
      }
    }

    Box(
      modifier = Modifier
        .weight(1f)
        .padding(8.dp)
        .wrapContentWidth(),
    ) {
      Image(
        modifier = Modifier.size(
          width = 150.dp,
          height = 151.dp,
        ).padding(vertical = 8.dp, horizontal = 8.dp),
        painter = painter,
        contentDescription = null,
      )
    }
  }
}

@Composable
private fun TagItem(tag: Tag) {
  Text(
    text = tag.title,
    color = tag.color,
    fontSize = 16.sp,
    textAlign = TextAlign.Center,
    modifier = Modifier
      .padding(2.dp)
      .wrapContentHeight(),
  )
}
