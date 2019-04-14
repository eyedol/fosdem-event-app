package com.addhen.fosdem.base.view.state

interface Reducer<S : State> {

    fun onAction(action: Action)
}
