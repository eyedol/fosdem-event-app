/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addhen.fosdem.sessions.view

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.addhen.fosdem.base.view.BaseViewModel
import com.addhen.fosdem.base.view.state.Action
import com.addhen.fosdem.data.repository.session.SessionRepository
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SessionsViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : BaseViewModel() {

    val viewState: LiveData<SessionState.ViewState>
        get() = mutableViewState
    val viewEffect: LiveData<SessionViewEffect>
        get() = mutableViewEffect

    private val mutableViewState = MutableLiveData<SessionState.ViewState>()
    private val mutableViewEffect = MutableLiveData<SessionViewEffect>()
    private var currentViewState = SessionState.ViewState()
        set(value) {
            field = value
            mutableViewState.value = value
        }
    val isEmptyViewShown = ObservableBoolean()
    val isBottomSheetCollapsed = ObservableBoolean()

    fun onAction(action: Action) {
        currentViewState = when (action) {
            is SessionAction.BottomSheetFilterToggled -> {
                mutableViewEffect.value = SessionViewEffect.BottomSheetToggled
                currentViewState.copy(bottomSheetState = BottomSheetBehavior.STATE_COLLAPSED)
            }
            is SessionAction.LoadSessions -> {
                currentViewState.copy(isLoading = false, isEmptyViewShown = true)
            }
            else -> {
                currentViewState.copy(isEmptyViewShown = true)
            }
        }
    }
}
