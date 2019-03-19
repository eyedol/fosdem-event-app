package com.addhen.fosdem.sessions.view

import com.addhen.fosdem.base.view.state.Action
import com.addhen.fosdem.base.view.state.State
import com.addhen.fosdem.data.model.Session
import com.addhen.fosdem.sessions.model.SessionScreen

sealed class SessionState : State {
    data class ViewState(
        val isEmptyViewShown: Boolean = false,
        val bottomSheetState: Int = 0,
        val sessions: List<Session> = emptyList()
    )
}


sealed class SessionAction : Action {

    object LoadSessions : SessionAction()

    data class BottomSheetFilterToggled(val sessionScreen: SessionScreen) : SessionAction()
}
