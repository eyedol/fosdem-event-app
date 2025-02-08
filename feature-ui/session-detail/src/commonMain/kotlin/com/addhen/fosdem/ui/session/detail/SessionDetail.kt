// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import com.addhen.fosdem.compose.common.ui.api.LoadingText
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.SnackbarMessageEffect
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.try_again
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.ui.session.detail.component.SessionBookmarkButton
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItem
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.addhen.fosdem.ui.session.detail.component.SessionDetailTopAppBar
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.stringResource

const val SessionDetailScreenTestTag = "SessionDetailScreen"

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
    message = viewState.message,
    onNavigationIconClick = { eventSink(SessionDetailUiEvent.GoToSessionList) },
    onBookmarkClick = { eventId ->
      eventSink(SessionDetailUiEvent.ToggleSessionBookmark(eventId))
    },
    onLinkClick = { url ->
      eventSink(SessionDetailUiEvent.ShowLink(url))
    },
    onCalendarRegistrationClick = { event ->
      eventSink(SessionDetailUiEvent.RegisterSessionToCalendar(event))
    },
    onShareClick = { event ->
      eventSink(SessionDetailUiEvent.ShareSession(event))
    },
    onMessageShown = { id ->
      eventSink(SessionDetailUiEvent.ClearMessage(id))
    },
    snackbarHostState,
    modifier,
  )
}

sealed class SessionDetailScreenUiState {
  data object Loading : SessionDetailScreenUiState()
  data class Loaded(
    val sessionDetailUiState: SessionDetailItemSectionUiState,
  ) : SessionDetailScreenUiState()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SessionItemDetailScreen(
  uiState: SessionDetailScreenUiState,
  message: UiMessage?,
  onNavigationIconClick: () -> Unit,
  onBookmarkClick: (Long) -> Unit,
  onLinkClick: (url: String) -> Unit,
  onCalendarRegistrationClick: (Event) -> Unit,
  onShareClick: (Event) -> Unit,
  onMessageShown: (id: Long) -> Unit,
  snackbarHostState: SnackbarHostState,
  modifier: Modifier = Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
  val listState = rememberLazyListState()
  val expanded by remember {
    derivedStateOf { listState.firstVisibleItemIndex > 0 }
  }

  SnackbarMessageEffect(
    snackbarHostState = snackbarHostState,
    message = message,
    actionLabel = stringResource(Res.string.try_again),
    onMessageShown = onMessageShown,
  )

  Scaffold(
    modifier = modifier
      .testTag(SessionDetailScreenTestTag)
      .fillMaxSize()
      .nestedScroll(scrollBehavior.nestedScrollConnection),
    contentWindowInsets = ScaffoldDefaults.contentWindowInsets
      .exclude(WindowInsets.navigationBars),
    topBar = {
      if (uiState is SessionDetailScreenUiState.Loaded) {
        SessionDetailTopAppBar(
          title = uiState.sessionDetailUiState.event.title,
          onNavigationIconClick = onNavigationIconClick,
          scrollBehavior = scrollBehavior,
          event = uiState.sessionDetailUiState.event,
          onCalendarRegistrationClick = onCalendarRegistrationClick,
          onShareClick = onShareClick,
        )
      }
    },
    floatingActionButton = {
      if (uiState is SessionDetailScreenUiState.Loaded) {
        val event = uiState.sessionDetailUiState.event
        SessionBookmarkButton(
          eventId = event.id,
          isBookmarked = event.isBookmarked,
          onBookmarkClick = onBookmarkClick,
          expanded = expanded,
        )
      }
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { innerPadding ->
    if (uiState is SessionDetailScreenUiState.Loaded) {
      SessionDetailItem(
        modifier = Modifier.fillMaxSize(),
        uiState = uiState.sessionDetailUiState,
        onLinkClick = onLinkClick,
        contentPadding = innerPadding,
        listState = listState,
      )
    }

    AnimatedVisibility(
      modifier = Modifier.fillMaxSize(),
      visible = (uiState is SessionDetailScreenUiState.Loading),
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
