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

package com.addhen.fosdem.session.data.repository.post

import androidx.annotation.WorkerThread
import com.addhen.fosdem.data.model.Session
import com.addhen.fosdem.data.repository.post.LocalDataSource
import com.addhen.fosdem.data.repository.post.SessionRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionDataRepository @Inject constructor(val local: LocalDataSource) : SessionRepository {

    suspend fun getSessions() = getSessions(10, 0)

    override suspend fun getSessions(limit: Int, page: Int): List<Session> {
        TODO()
    }

    @WorkerThread
    override suspend fun getSession(id: Long): Session {
        TODO()
    }
}
