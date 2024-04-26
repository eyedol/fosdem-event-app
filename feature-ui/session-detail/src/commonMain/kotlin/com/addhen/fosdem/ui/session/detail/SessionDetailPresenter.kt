// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import co.touchlab.kermit.Logger
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.UiMessage
import com.addhen.fosdem.compose.common.ui.api.UiMessageManager
import com.addhen.fosdem.core.api.screens.CalendarScreen
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.core.api.screens.ShareScreen
import com.addhen.fosdem.core.api.screens.UrlScreen
import com.addhen.fosdem.core.api.timeZoneBrussels
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.model.api.descriptionFullText
import com.addhen.fosdem.model.api.endAtLocalDateTime
import com.addhen.fosdem.model.api.startAtLocalDateTime
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.slack.circuit.retained.collectAsRetainedState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.datetime.toInstant
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
    val scope = rememberCoroutineScope()
    val uiMessageManager = remember { UiMessageManager() }
    val appString = LocalStrings.current

    fun eventSink(event: SessionDetailUiEvent) {
      when (event) {
        SessionDetailUiEvent.GoToSession -> navigator.pop()
        is SessionDetailUiEvent.RegisterSessionToCalendar -> {
          val localEvent = event.event
          val text =
            """
              <br>|Speaker: ${localEvent.speakers.joinToString { speaker -> speaker.name }}</br>
              <br>|Url: ${localEvent.url}</br>
              <br>|---</br>
              |Description: ${localEvent.descriptionFullText}
            """.trimIndent()
          navigator.goTo(
            CalendarScreen(
              localEvent.title,
              localEvent.room.name,
              text,
              localEvent.startAtLocalDateTime.toInstant(timeZoneBrussels).toEpochMilliseconds(),
              localEvent.endAtLocalDateTime.toInstant(timeZoneBrussels).toEpochMilliseconds(),
            ),
          )
        }
        is SessionDetailUiEvent.ShareSession -> {
          val localEvent = event.event

          val text =
            """
                <br>|Title: ${localEvent.title}</br>
                <br>|Schedule: ${localEvent.day.date}: ${localEvent.startAt} - ${localEvent.endAt}</br>
                <br>|Room: ${localEvent.room.name}</br>
                <br>|Speaker: ${localEvent.speakers.joinToString { speaker -> speaker.name }}</br>
                <br>|Url: ${localEvent.url}</br>
                <br>|---</br>
                |Description: ${localEvent.descriptionFullText}
            """.trimMargin()
          navigator.goTo(ShareScreen(text))
        }
        is SessionDetailUiEvent.ToggleSessionBookmark -> {
          scope.launch {
            repository.value.toggleBookmark(event.eventId)
          }
        }
        is SessionDetailUiEvent.ShowLink -> navigator.goTo(UrlScreen(event.url))
      }
    }

    val uiState by repository.value.getEvent(screen.eventId).map { event ->
      SessionDetailScreenUiState.Loaded(
        sessionDetailUiState = SessionDetailItemSectionUiState(event),
      )
    }.catch {
      Logger.e(it) { "Error occurred" }
      uiMessageManager.emitMessage(
        UiMessage(
          it,
          actionLabel = appString.tryAgain,
        ),
      )
    }.collectAsRetainedState(SessionDetailScreenUiState.Loading)

    return SessionDetailUiState(
      sessionDetailScreenUiState = uiState,
      eventSink = ::eventSink,
    )
  }
}
