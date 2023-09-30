// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material.icons.outlined.ViewTimeline
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
import com.addhen.fosdem.ui.session.component.SessionUiType

const val SearchButtonTestTag = "SearchButton"
const val SessionUiTypeChangeButtonTestTag = "SessionUiTypeChangeButton"

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SessionTopArea(
  sessionUiType: SessionUiType,
  appStrings: AppStrings,
  onSessionUiChangeClick: () -> Unit,
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
      IconButton(
        modifier = Modifier.testTag(SessionUiTypeChangeButtonTestTag),
        onClick = { onSessionUiChangeClick() },
      ) {
        Icon(
          imageVector = if (sessionUiType != SessionUiType.Grid) {
            Icons.Outlined.GridView
          } else {
            Icons.Outlined.ViewTimeline
          },
          contentDescription = if (sessionUiType != SessionUiType.Grid) {
            appStrings.toggleSessionListContentDescription
          } else {
            appStrings.toggleSessionListContentDescription
          },
        )
      }
    },
    colors = TopAppBarDefaults.largeTopAppBarColors(
      containerColor = Color.Transparent,
    ),
  )
}
