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
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

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
          contentDescription = LocalStrings.current.refresh,
        )
      }
    }
  }
}
