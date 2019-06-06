package com.addhen.fosdem.sessions.view

import com.addhen.fosdem.base.view.state.Action
import com.addhen.fosdem.base.view.state.Effect
import com.addhen.fosdem.base.view.state.State
import com.addhen.fosdem.data.model.Session
import com.addhen.fosdem.sessions.model.ScreenTab

data class SessionState(
    val isEmptyViewShown: Boolean = false,
    val bottomSheetState: Int = 0,
    val isLoading: Boolean = false,
    val sessions: List<Session> = emptyList()
) : State

sealed class SessionAction : Action {
    object LoadSessions : SessionAction()
    object SessionLoaded : SessionAction()
    data class BottomSheetFilterToggled(val screenTab: ScreenTab) : SessionAction()
}

sealed class SessionViewEffect : Effect {
    object BottomSheetToggled : SessionViewEffect()
}
