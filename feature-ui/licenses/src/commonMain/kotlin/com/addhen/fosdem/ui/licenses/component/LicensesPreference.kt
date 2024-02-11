package com.addhen.fosdem.ui.licenses.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Preference(
  title: String,
  modifier: Modifier = Modifier,
  summary: (@Composable () -> Unit)? = null,
  control: (@Composable () -> Unit)? = null,
) {
  Surface(modifier = modifier) {
    Row(
      modifier = Modifier.padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.spacedBy(2.dp),
      ) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodyLarge,
        )

        if (summary != null) {
          ProvideTextStyle(
            MaterialTheme.typography.bodyMedium.copy(
              color = MaterialTheme.colorScheme.onSurfaceVariant,
            ),
          ) {
            summary()
          }
        }
      }

      control?.invoke()
    }
  }
}

@Composable
fun PreferenceHeader(
  title: String,
  modifier: Modifier = Modifier,
  tonalElevation: Dp = 0.dp,
) {
  Surface(modifier = modifier, tonalElevation = tonalElevation) {
    Text(
      text = title,
      style = MaterialTheme.typography.labelLarge,
      modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 4.dp),
    )
  }
}
