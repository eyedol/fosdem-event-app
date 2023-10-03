package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings

@Composable
fun EmptySearchResultBody(
  query: String,
  modifier: Modifier = Modifier,
) {
  val appStrings = LocalStrings.current
  Box(
    modifier = modifier
      .fillMaxSize(),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      modifier = Modifier.wrapContentSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(text = "\uD83D\uDD75️\u200D♂️")
      Spacer(modifier = Modifier.height(28.dp))
      Text(
        text = appStrings.searchNotFound(query),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onBackground,
      )
    }
  }
}

