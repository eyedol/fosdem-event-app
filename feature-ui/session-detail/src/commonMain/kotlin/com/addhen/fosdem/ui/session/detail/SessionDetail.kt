// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.addhen.fosdem.compose.common.ui.api.LoadingText
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.detail.component.SessionDetailBottomAppBar
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItem
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.addhen.fosdem.ui.session.detail.component.SessionDetailTopAppBar
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

const val SessionScreenTestTag = "SessionDetailScreen"

@Inject
class SessionDetailUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is SessionDetailScreen -> {
      ui<SessionDetailUiState> { state, modifier ->
        SessionDetail(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun SessionDetail(
  viewState: SessionDetailUiState,
  modifier: Modifier = Modifier,
) {
  val snackbarHostState = remember { SnackbarHostState() }
  val eventSink = viewState.eventSink

  SessionItemDetailScreen(
    uiState = viewState.sessionDetailScreenUiState,
    onNavigationIconClick = { eventSink(SessionDetailUiEvent.GoToSession) },
    onBookmarkClick = { eventId, isBookmarked ->
      eventSink(SessionDetailUiEvent.ToggleSessionBookmark(eventId, isBookmarked))
    },
    onLinkClick = {},
    onCalendarRegistrationClick = { event ->
      eventSink(SessionDetailUiEvent.RegisterSessionToCalendar(event))
    },
    onShareClick = { event ->
      eventSink(SessionDetailUiEvent.ShareSession(event))
    },
    snackbarHostState,
    modifier,
  )
}

sealed class ScreenDetailScreenUiState {
  data object Loading : ScreenDetailScreenUiState()
  data class Loaded(
    val sessionDetailUiState: SessionDetailItemSectionUiState,
    val viewBookmarkListRequestState: ViewBookmarkListRequestState,
  ) : ScreenDetailScreenUiState()

  val shouldNavigateToBookmarkList: Boolean
    get() = this is Loaded && viewBookmarkListRequestState is ViewBookmarkListRequestState.Requested
}

sealed class ViewBookmarkListRequestState {
  data object NotRequested : ViewBookmarkListRequestState()
  data object Requested : ViewBookmarkListRequestState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SessionItemDetailScreen(
  uiState: ScreenDetailScreenUiState,
  onNavigationIconClick: () -> Unit,
  onBookmarkClick: (Long, Boolean) -> Unit,
  onLinkClick: (url: String) -> Unit,
  onCalendarRegistrationClick: (Event) -> Unit,
  onShareClick: (Event) -> Unit,
  snackbarHostState: SnackbarHostState,
  modifier: Modifier = Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  val appStrings = LocalStrings.current
  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = ScaffoldDefaults.contentWindowInsets
      .exclude(WindowInsets.navigationBars),
    topBar = {
      if (uiState is ScreenDetailScreenUiState.Loaded) {
        SessionDetailTopAppBar(
          title = uiState.sessionDetailUiState.event.title,
          onNavigationIconClick = onNavigationIconClick,
          scrollBehavior = scrollBehavior,
        )
      }
    },
    bottomBar = {
      if (uiState is ScreenDetailScreenUiState.Loaded) {
        SessionDetailBottomAppBar(
          event = uiState.sessionDetailUiState.event,
          isBookmarked = uiState.sessionDetailUiState.event.isBookmarked,
          addFavorite = appStrings.addToFavoritesTitle,
          removeFavorite = appStrings.removeFromFavorites,
          shareTitle = appStrings.shareTitle,
          addToCalendar = appStrings.addToCalendarTitle,
          onBookmarkClick = onBookmarkClick,
          onCalendarRegistrationClick = onCalendarRegistrationClick,
          onShareClick = onShareClick,
        )
      }
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { innerPadding ->
    if (uiState is ScreenDetailScreenUiState.Loaded) {
      SessionDetailItem(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.sessionDetailUiState,
        onLinkClick = onLinkClick,
        contentPadding = innerPadding,
      )
    }

    AnimatedVisibility(
      modifier = Modifier.fillMaxSize(),
      visible = (uiState is ScreenDetailScreenUiState.Loading),
      enter = fadeIn(),
      exit = fadeOut(),
    ) {
      LoadingText(
        modifier = Modifier
          .fillMaxSize()
          .background(MaterialTheme.colorScheme.background),
      )
    }
  }
}
