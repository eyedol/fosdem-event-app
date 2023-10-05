// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.list

import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.AppImage
import com.addhen.fosdem.compose.common.ui.api.imageResource
import com.addhen.fosdem.compose.common.ui.api.theme.tagColors
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.SessionScreen
import com.addhen.fosdem.core.api.screens.SessionSearchScreen
import com.addhen.fosdem.model.api.Day
import com.addhen.fosdem.model.api.day
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.model.api.day2
import com.addhen.fosdem.model.api.day2Event
import com.addhen.fosdem.ui.session.component.DayTab
import com.addhen.fosdem.ui.session.component.SessionListUiState
import com.addhen.fosdem.ui.session.component.Tag
import com.addhen.fosdem.ui.session.list.component.SessionSheetUiState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<SessionUiState> {
  @Composable
  override fun present(): SessionUiState {
    // val scope = rememberCoroutineScope()

    fun eventSink(event: SessionUiEvent) {
      when (event) {
        is SessionUiEvent.GoToSessionDetails -> {
          navigator.goTo(SessionDetailScreen(event.eventId))
        }
        SessionUiEvent.SearchSession -> navigator.goTo(SessionSearchScreen)
        is SessionUiEvent.ToggleSessionBookmark -> TODO()
        SessionUiEvent.ToggleSessionUi -> TODO()
      }
    }

    return SessionUiState(
      isRefreshing = false,
      appTitle = "FOSDEM",
      appLogo = imageResource(AppImage.FosdemLogo),
      year = "24",
      location = "@ Brussels, Belgium",
      tags = tags(),
      content = sessionSheetPreview(),
      eventSink = ::eventSink,
    )
  }

  @Composable
  private fun tags() = listOf(
    Tag("beer", tagColors().tagColorMain),
    Tag("open source", tagColors().tagColorAlt),
    Tag("free software", tagColors().tagColorMain),
    Tag("lightning talks", tagColors().tagColorAlt),
    Tag("devrooms", tagColors().tagColorMain),
    Tag("800+ talks", tagColors().tagColorAlt),
    Tag("8000+ hackers", tagColors().tagColorMain),
  ).toPersistentList()

  private fun sessionSheetPreview(): SessionSheetUiState {
    val sessionListUiState = SessionListUiState(
      sortAndGroupedEventsItems,
    )

    val sessionListUiState2 = SessionListUiState(
      sortAndGroupedEventsItems2,
    )

    val dayTab = day.toDayTab()
    val dayTab2 = day2.toDayTab()

    return SessionSheetUiState.ListSession(
      days = listOf(dayTab, dayTab2).toPersistentList(),
      sessionListUiStates = mapOf(
        dayTab to sessionListUiState,
        dayTab2 to sessionListUiState2,
      ),
    )
  }

  fun Day.toDayTab() = DayTab(
    id = id,
    date = date,
  )

  val sortAndGroupedEventsItems = listOf(day1Event, day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()

  val sortAndGroupedEventsItems2 = listOf(day2Event).groupBy {
    it.startTime.toString() + it.duration.toString()
  }.mapValues { entries ->
    entries.value.sortedWith(
      compareBy({ it.day.date.toString() }, { it.startTime.toString() }),
    )
  }.toPersistentMap()
}
