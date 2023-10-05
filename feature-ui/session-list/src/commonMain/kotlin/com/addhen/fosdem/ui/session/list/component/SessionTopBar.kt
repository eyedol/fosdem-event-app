// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.core.api.i18n.AppStrings
import com.addhen.fosdem.ui.session.component.RefreshButton

const val SearchButtonTestTag = "SearchButton"
const val SessionRefreshButtonTestTag = "SessionRefreshButton"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SessionTopArea(
  isRefreshing: Boolean,
  appStrings: AppStrings,
  onRefreshClick: () -> Unit,
  onSearchClick: () -> Unit,
  titleIcon: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    title = titleIcon,
    modifier = modifier,
    actions = {
      IconButton(
        modifier = Modifier.testTag(SearchButtonTestTag),
        onClick = { onSearchClick() },
      ) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = appStrings.searchContentDescription,
        )
      }
      RefreshButton(
        refreshing = isRefreshing,
        onClick = { onRefreshClick() },
        modifier = Modifier.testTag(SessionRefreshButtonTestTag),
      )
    },
    colors = TopAppBarDefaults.largeTopAppBarColors(
      containerColor = Color.Transparent,
    ),
  )
}
