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

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.addhen.fosdem.base.CoroutineDispatchers
import com.addhen.fosdem.base.Resource
import com.addhen.fosdem.base.view.BaseViewModel
import com.addhen.fosdem.data.repository.session.SessionRepository
import com.addhen.fosdem.sessions.model.Screen
import javax.inject.Inject

class SessionsViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val sessionRepository: SessionRepository
) : BaseViewModel(dispatchers), LifecycleObserver {

    val screens = MutableLiveData<Resource<List<Screen.Tab>>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onSwipeRefresh() {
        loadScreens()
    }

    private fun loadScreens() {
        screens.value = Resource.success(Screen.tabs)
    }
}
