// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.core.api.i18n.EnAppStrings
import com.addhen.fosdem.core.api.screens.SessionDetailScreen
import com.addhen.fosdem.model.api.day1Event
import com.addhen.fosdem.ui.session.detail.component.SessionDetailItemSectionUiState
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import me.tatarka.inject.annotations.Assisted
import me.tatarka.inject.annotations.Inject

@Inject
class SessionDetailUiPresenterFactory(
  private val presenterFactory: (Navigator) -> SessionDetailPresenter,
) : Presenter.Factory {
  override fun create(
    screen: Screen,
    navigator: Navigator,
    context: CircuitContext,
  ): Presenter<*>? {
    return when (screen) {
      is SessionDetailScreen -> presenterFactory(navigator)
      else -> null
    }
  }
}

@Inject
class SessionDetailPresenter(
  @Assisted private val navigator: Navigator,
) : Presenter<SessionDetailUiState> {
  @Composable
  override fun present(): SessionDetailUiState {
    val scope = rememberCoroutineScope()

    fun eventSink(event: SessionDetailUiEvent) {
      when (event) {
        SessionDetailUiEvent.GoToSession -> TODO()
        is SessionDetailUiEvent.RegisterSessionToCalendar -> TODO()
        is SessionDetailUiEvent.ShareSession -> TODO()
        is SessionDetailUiEvent.ToggleSessionBookmark -> TODO()
      }
    }

    val appStrings = LocalStrings.current
    val sessionDetailUiState = SessionDetailItemSectionUiState(
      event = day1Event,
      dateTitle = appStrings.dateTitle,
      placeTitle = appStrings.roomTitle,
      trackTitle = appStrings.trackTitle,
      readMoreTitle = appStrings.readMoreLabel,
      speakerTitle = appStrings.speakerTitle,
      attachmentTitle = appStrings.attachmentTitle,
      linkTitle = appStrings.linkTitle,
    )
    val uiState = ScreenDetailScreenUiState.Loaded(
      sessionDetailUiState = sessionDetailUiState,
      appStrings = appStrings,
      viewBookmarkListRequestState = ViewBookmarkListRequestState.Requested,
    )

    // TODO load session types
    return SessionDetailUiState(
      sessionDetailScreenUiState = uiState,
      eventSink = ::eventSink,
    )
  }
}
