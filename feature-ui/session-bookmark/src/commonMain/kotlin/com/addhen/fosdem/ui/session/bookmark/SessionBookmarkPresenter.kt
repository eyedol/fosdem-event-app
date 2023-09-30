// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.bookmark

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2Event
import com.addhen.fosdem.ui.session.bookmark.component.SessionBookmarkSheetUiState
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.toPersistentMap
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
) : Presenter<SessionBookmarkUiState> {
  @Composable
  override fun present(): SessionBookmarkUiState {
    //val scope = rememberCoroutineScope()

    fun eventSink(event: SessionBookmarkUiEvent) {
      when (event) {
        is SessionBookmarkUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }
        SessionBookmarkUiEvent.FilterAllBookmarks -> TODO()
        SessionBookmarkUiEvent.FilterFirstDayBookmarks -> TODO()
        SessionBookmarkUiEvent.FilterSecondDayBookmarks -> TODO()
        SessionBookmarkUiEvent.GoToPreviousScreen -> TODO()
        is SessionBookmarkUiEvent.ToggleSessionBookmark -> TODO()
      }
    }

    return SessionBookmarkUiState(
      content = sessionSheetPreview(),
      eventSink = ::eventSink,
    )
  }

  private fun sessionSheetPreview(): SessionBookmarkSheetUiState {
    return SessionBookmarkSheetUiState.ListBookmark(
      sessionItemMap = sortAndGroupedEventsItems,
      isAllSelected = true,
      isDayFirstSelected = false,
      isDaySecondSelected = false,
    )
  }

  val sortAndGroupedEventsItems = listOf(day1Event, day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()
}
