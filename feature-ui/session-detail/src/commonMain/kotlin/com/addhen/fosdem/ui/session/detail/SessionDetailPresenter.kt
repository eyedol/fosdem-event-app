// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.ShareScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionDetailUiPresenterFactory(
  private val presenterFactory: (SessionDetailScreen, Navigator) -> SessionDetailPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionDetailScreen -> presenterFactory(screen, navigator)
      else -> null
    }
  }
}

@Inject
class SessionDetailPresenter(
  @Assisted private val screen: SessionDetailScreen,
  @Assisted private val navigator: Navigator,
  private val repository: Lazy<EventsRepository>,
) : Presenter<SessionDetailUiState> {
  @Composable
  override fun present(): SessionDetailUiState {
    val uiMessageManager = remember { UiMessageManager() }
    val appString = LocalStrings.current

    fun eventSink(event: SessionDetailUiEvent) {
      when (event) {
        SessionDetailUiEvent.GoToSession -> navigator.pop()
        is SessionDetailUiEvent.RegisterSessionToCalendar -> {}
        is SessionDetailUiEvent.ShareSession -> {
          val localEvent = event.event

          val description = when {
            localEvent.description.isBlank().not() && localEvent.abstractText.isBlank().not() -> {
              "${localEvent.abstractText}\n\n${localEvent.description}"
            }
            localEvent.description.isBlank().not() -> localEvent.description
            else -> localEvent.abstractText
          }

          val text =
            """
                |Title: ${localEvent.title}
                |Schedule: ${localEvent.day.date}: ${localEvent.startAt} - ${localEvent.endAt}
                |Room: ${localEvent.room.name}
                |Speaker: ${localEvent.speakers.joinToString { speaker -> speaker.name }}
                |---
                |Description: $description
                """.trimMargin()
          navigator.goTo(ShareScreen(text))
        }
        is SessionDetailUiEvent.ToggleSessionBookmark -> {}
        is SessionDetailUiEvent.ShowLink -> navigator.goTo(UrlScreen(event.url))
      }
    }

    val uiState by repository.value.getEvent(screen.eventId).map { event ->
      ScreenDetailScreenUiState.Loaded(
        sessionDetailUiState = SessionDetailItemSectionUiState(event),
        viewBookmarkListRequestState = ViewBookmarkListRequestState.Requested,
      )
    }.catch {
      Logger.e(it) { "Error occurred" }
      uiMessageManager.emitMessage(
        UiMessage(
          it,
          actionLabel = appString.tryAgain,
        ),
      )
    }.collectAsRetainedState(ScreenDetailScreenUiState.Loading)

    return SessionDetailUiState(
      sessionDetailScreenUiState = uiState,
      eventSink = ::eventSink,
    )
  }
}
