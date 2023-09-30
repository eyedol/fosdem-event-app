// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

private val tabMinHeight = 32.dp
private val tabMaxHeight = 56.dp
private val tabIndicatorHorizontalGap = 8.dp
private val tabRowHorizontalSpacing = 16.dp - (tabIndicatorHorizontalGap / 2)
private val tabRowTopSpacing = 16.dp
private val tabRowBottomSpacing = 12.dp
private val tabRowMinHeight = tabMinHeight + tabRowTopSpacing + tabRowBottomSpacing
private val tabRowMaxHeight = tabMaxHeight + tabRowTopSpacing + tabRowBottomSpacing

@Composable
fun SessionTab(
  tabTitle: String,
  selected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Tab(
    selected = selected,
    onClick = onClick,
    content = {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 4.dp),
      ) {
        Text(
          text = tabTitle,
          style = MaterialTheme.typography.labelMedium,
        )
      }
    },
    selectedContentColor = MaterialTheme.colorScheme.onPrimary,
    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = modifier.heightIn(min = tabMinHeight),
  )
}

@Composable
fun SessionTabRow(
  tabState: SessionTabState,
  selectedTabIndex: Int,
  modifier: Modifier = Modifier,
  indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { tabPositions ->
    if (selectedTabIndex < tabPositions.size) {
      SessionTabIndicator(
        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
      )
    }
  },
  tabs: @Composable () -> Unit,
) {
  TabRow(
    selectedTabIndex = selectedTabIndex,
    modifier = modifier
      .defaultMinSize(
        minHeight = tabRowMaxHeight - (
          (tabRowMaxHeight - tabRowMinHeight) * tabState.tabCollapseProgress
          ),
      )
      .padding(
        start = tabRowHorizontalSpacing,
        top = tabRowTopSpacing,
        end = tabRowHorizontalSpacing,
        bottom = tabRowBottomSpacing,
      ),
    divider = {},
    indicator = indicator,
    tabs = tabs,
  )
}

@Composable
fun SessionTabIndicator(
  modifier: Modifier = Modifier,
) {
  Box(
    modifier
      .zIndex(-1f)
      .padding(horizontal = tabIndicatorHorizontalGap / 2)
      .fillMaxSize()
      .background(
        color = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(50),
      ),
  )
}

@Composable
fun rememberSessionTabState(initialScrollOffset: Float = 0.0f): SessionTabState {
  val offsetLimit = LocalDensity.current.run {
    (tabRowMaxHeight - tabRowMinHeight).toPx()
  }
  return rememberSaveable(saver = SessionTabState.Saver) {
    SessionTabState(
      initialScrollOffset = initialScrollOffset,
      initialOffsetLimit = -offsetLimit,
    )
  }
}

@Stable
class SessionTabState(
  initialOffsetLimit: Float = 0f,
  initialScrollOffset: Float = 0f,
) {

  private val scrollOffsetLimit by mutableFloatStateOf(initialOffsetLimit)

  val tabCollapseProgress: Float
    get() = scrollOffset / scrollOffsetLimit

  private val _scrollOffset = mutableFloatStateOf(initialScrollOffset)

  var scrollOffset: Float
    get() = _scrollOffset.value
    private set(newOffset) {
      _scrollOffset.value = newOffset.coerceIn(
        minimumValue = scrollOffsetLimit,
        maximumValue = 0f,
      )
    }

  val isTabExpandable: Boolean
    get() = scrollOffset > scrollOffsetLimit

  val isTabCollapsing: Boolean
    get() = scrollOffset != 0f

  fun onScroll(y: Float) {
    scrollOffset += y
  }

  companion object {
    val Saver: Saver<SessionTabState, *> = listSaver(
      save = { listOf(it.scrollOffsetLimit, it.scrollOffset) },
      restore = {
        SessionTabState(
          initialOffsetLimit = it[0],
          initialScrollOffset = it[1],
        )
      },
    )
  }
}
