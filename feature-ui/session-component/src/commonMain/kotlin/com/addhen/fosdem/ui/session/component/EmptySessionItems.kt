// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SessionEmptyListView(
  title: String,
  description: String,
  modifier: Modifier = Modifier,
  graphicContent: @Composable () -> Unit,
) {
  Box(
    modifier = modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      modifier = Modifier
        .padding(start = 16.dp, end = 16.dp)
        .wrapContentSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      val density = LocalDensity.current
      val emojiHeaderGraphicTextStyle = remember(density) {
        TextStyle(
          fontSize = 96.dp.asEm(density),
          color = Color.Red,
        )
      }

      ProvideTextStyle(emojiHeaderGraphicTextStyle) {
        Box(Modifier.align(Alignment.CenterHorizontally)) {
          graphicContent()
        }
      }
      Spacer(modifier = Modifier.height(28.dp))
      Text(
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.size(8.dp))
      Text(
        text = description,
        style = MaterialTheme.typography.bodyMedium.copy(
          fontStyle = FontStyle.Italic
        ),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}

private fun Dp.asEm(density: Density): TextUnit = (value / density.fontScale).sp
