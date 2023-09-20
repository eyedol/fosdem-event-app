// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.theme.fosdem_pink

@Composable
fun SessionHeader(painter: Painter, modifier: Modifier = Modifier) {
  Row(
    modifier = modifier.wrapContentSize(),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(Modifier.padding(horizontal = 16.dp)) {
      Text(
        text = buildAnnotatedString {
          withStyle(style = MaterialTheme.typography.displaySmall.toSpanStyle()) {
            append("FOSDEM")
          }
          withStyle(
            style = MaterialTheme.typography.displaySmall
              .toSpanStyle()
              .copy(color = fosdem_pink),
          ) {
            append("\u02BC24")
          }
        },
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = "@ Brussels, Belgium",
        style = MaterialTheme.typography.labelMedium,
      )
    }
    Box {
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
