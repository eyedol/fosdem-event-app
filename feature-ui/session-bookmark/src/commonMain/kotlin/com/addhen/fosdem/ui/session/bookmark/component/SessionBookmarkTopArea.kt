// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.bookmark_title
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionBookmarkTopArea(
  onBackClick: () -> Unit,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

  TopAppBar(
    title = {
      if (scrollBehavior.state.overlappedFraction == 0f) {
        Text(
          text = stringResource(Res.string.bookmark_title),
          style = MaterialTheme.typography.headlineLarge,
          fontWeight = FontWeight.Medium,
        )
      } else {
        Text(
          text = stringResource(Res.string.bookmark_title),
          style = MaterialTheme.typography.titleLarge,
          modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
        )
      }
    },
    navigationIcon = {
      IconButton(onClick = onBackClick) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = null,
        )
      }
    },
    scrollBehavior = scrollBehavior,
  )
}
