// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.onException
import com.addhen.fosdem.core.api.screens.SessionBookmarkScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.Event
import com.addhen.fosdem.model.api.sortAndGroupedEventsItems
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.dayTabs
import com.addhen.fosdem.ui.session.component.toDayTab
import com.addhen.fosdem.ui.session.list.component.SessionsSheetUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionsPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionsScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionsPresenter(
  @Assisted private val navigator: Navigator,
  private val repository: Lazy<EventsRepository>,
) : Presenter<SessionsUiState> {
  @Composable
  override fun present(): SessionsUiState {
    val scope = rememberCoroutineScope()
    val days by rememberRetained { mutableStateOf(dayTabs) }
    var isRefreshing by rememberRetained { mutableStateOf(false) }
    val uiMessageManager = remember { UiMessageManager() }
    val message by uiMessageManager.message.collectAsState(null)

    LaunchedEffect(isRefreshing) {
      if (isRefreshing) {
        val results = repository.value.refresh()
        results.onException {
          Logger.e(it) { "Error occurred refreshing events" }
          uiMessageManager.emitMessage(
            UiMessage(it),
          )
        }
        isRefreshing = false
      }
    }

    val events by repository.value.getEvents().map { events ->
      successSessionSheetUiSate(events, days)
    }.catch {
      Logger.e(it) { "Error occurred" }
      uiMessageManager.emitMessage(UiMessage(it))
    }.collectAsRetainedState(SessionsSheetUiState.Loading(days))

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        is SessionUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }

        is SessionUiEvent.ToggleSessionBookmark -> {
          scope.launch {
            repository.value
              .toggleBookmark(event.eventId)
              .onException {
                Logger.e(it) { "Error occurred while toggling bookmark" }
                uiMessageManager.emitMessage(UiMessage(it))
              }
          }
        }

        SessionUiEvent.GoToBookmarkSessions -> navigator.goTo(SessionBookmarkScreen)
        SessionUiEvent.RefreshSession -> isRefreshing = true
        is SessionUiEvent.ClearMessage -> {
          scope.launch { uiMessageManager.clearMessage(event.messageId) }
        }
      }
    }

    return SessionsUiState(
      isRefreshing = isRefreshing,
      content = events,
      message = message,
      eventSink = ::eventSink,
    )
  }

  private fun successSessionSheetUiSate(
    results: List<Event>,
    days: PersistentList<DayTab>,
  ): SessionsSheetUiState {
    if (results.isEmpty()) return SessionsSheetUiState.Empty(days)
    val sessionGroupedAndMapWithDays = results.groupAndMapEventsWithDays()
    return SessionsSheetUiState.ListSession(
      days = days,
      sessionListUiStates = sessionGroupedAndMapWithDays,
    )
  }

  private fun List<Event>.groupAndMapEventsWithDays(): PersistentMap<DayTab, SessionListUiState> {
    val groupEventsByDay = groupBy { it.day }
    val sessionsWithDays = mutableMapOf<DayTab, SessionListUiState>()
    groupEventsByDay.asSequence().forEach { (key, events) ->
      val sortedAndGroupEvents = events.sortAndGroupedEventsItems()
      sessionsWithDays[key.toDayTab()] = SessionListUiState(sortedAndGroupEvents)
    }
    return sessionsWithDays.toPersistentMap()
  }
}
