package com.addhen.fosdem.sessions.view

import com.addhen.fosdem.base.view.state.Action
import com.addhen.fosdem.base.view.state.Effect
import com.addhen.fosdem.base.view.state.State
import com.addhen.fosdem.data.model.Session
import com.addhen.fosdem.sessions.model.SessionScreen

sealed class SessionState : State {
    data class ViewState(
        val isEmptyViewShown: Boolean = false,
        val bottomSheetState: Int = 0,
        val isLoading: Boolean = false,
        val sessions: List<Session> = emptyList()
    )
}


sealed class SessionAction : Action {
    object LoadSessions : SessionAction()
    data class BottomSheetFilterToggled(val sessionScreen: SessionScreen) : SessionAction()
}

sealed class SessionViewEffect : Effect {
    object BottomSheetToggled : SessionViewEffect()
}
