// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.onException
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.addhen.fosdem.ui.session.common.SessionFilters
import com.addhen.fosdem.ui.session.component.saturdayTab
import com.addhen.fosdem.ui.session.component.sundayTab
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionBookmarkUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionBookmarkPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionBookmarkScreen -> {
        presenterFactory(navigator)
      }

      else -> null
    }
  }
}

@Inject
class SessionBookmarkPresenter(
  @Assisted private val navigator: Navigator,
  private val eventsRepository: Lazy<EventsRepository>,
) : BaseBookmarkSessionUiPresenter(eventsRepository.value) {
  @Composable
  override fun present(): SessionBookmarkUiState {
    val scope = rememberCoroutineScope()

    var selectedFilters by rememberSaveable(stateSaver = SessionFilters.Saver) {
      mutableStateOf(SessionFilters())
    }

    val bookmarkSheetUiState by observeSessionFiltersAction
      .collectAsRetainedState(SessionBookmarkSheetUiState.Loading())
    val uiMessageManager = remember { UiMessageManager() }
    val message by uiMessageManager.message.collectAsState(null)

    LaunchedEffect(selectedFilters) {
      tryEmit(selectedFilters)
    }

    fun eventSink(event: SessionBookmarkUiEvent) {
      when (event) {
        is SessionBookmarkUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        SessionBookmarkUiEvent.FilterFirstDayBookmarks -> {
          selectedFilters = onDaySelected(selectedFilters, saturdayTab)
        }

        SessionBookmarkUiEvent.FilterSecondDayBookmarks -> {
          selectedFilters = onDaySelected(selectedFilters, sundayTab)
        }

        is SessionBookmarkUiEvent.ToggleSessionBookmark -> {
          scope.launch {
            eventsRepository.value.toggleBookmark(event.eventId)
              .onException {
                Logger.e(it) { "Error occurred while toggling bookmark" }
                uiMessageManager.emitMessage(
                  UiMessage(it),
                )
              }
          }
        }

        SessionBookmarkUiEvent.GoToPreviousScreen -> navigator.pop()
        SessionBookmarkUiEvent.ClearMessage -> {
          scope.launch { uiMessageManager.clearMessage(message!!.id) }
        }
      }
    }

    return SessionBookmarkUiState(
      content = bookmarkSheetUiState,
      message = message,
      eventSink = ::eventSink,
    )
  }
}
