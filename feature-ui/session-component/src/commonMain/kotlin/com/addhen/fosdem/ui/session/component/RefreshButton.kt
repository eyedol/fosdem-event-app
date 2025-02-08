// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.refresh
import org.jetbrains.compose.resources.stringResource

@Composable
fun RefreshButton(
  refreshing: Boolean,
  progressColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  IconButton(
    onClick = onClick,
    enabled = !refreshing,
    modifier = modifier,
  ) {
    Crossfade(refreshing) { targetRefreshing ->
      if (targetRefreshing) {
        AutoSizedCircularProgressIndicator(
          color = progressColor,
          modifier = Modifier
            .size(20.dp)
            .padding(2.dp),
        )
      } else {
        Icon(
          imageVector = Icons.Default.Refresh,
          contentDescription = stringResource(Res.string.refresh),
        )
      }
    }
  }
}
