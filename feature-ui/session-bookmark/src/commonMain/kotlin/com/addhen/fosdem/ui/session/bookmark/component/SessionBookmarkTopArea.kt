// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionBookmarkTopArea(
  onBackClick: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  val appStrings = LocalStrings.current

  TopAppBar(
    title = {
      if (scrollBehavior.state.overlappedFraction == 0f) {
        Text(
          text = appStrings.bookmarkTitle,
          style = MaterialTheme.typography.headlineLarge,
          fontWeight = FontWeight.Medium,
        )
      } else {
        Text(
          text = appStrings.bookmarkTitle,
          style = MaterialTheme.typography.titleLarge,
          modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
        )
      }
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.Default.ArrowBack,
          contentDescription = null,
        )
      }
    },
    scrollBehavior = scrollBehavior,
  )
}
