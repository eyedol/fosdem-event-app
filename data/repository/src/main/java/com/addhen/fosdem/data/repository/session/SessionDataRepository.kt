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

package com.addhen.fosdem.data.repository.session

import androidx.annotation.WorkerThread
import com.addhen.fosdem.api.FosdemApi
import com.addhen.fosdem.data.db.SessionDatabase
import com.addhen.fosdem.data.model.Session
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionDataRepository @Inject constructor(
    private val apiClient: FosdemApi,
    private val database: SessionDatabase
) : SessionRepository {

    override suspend fun getSessions(): List<Session> = coroutineScope {
        val sessionsAsync = async { database.sessions() }
        val speakersAsync = async { database.speakers() }
        val linksAsync = async { database.links() }
        val sessionEntities = sessionsAsync.await()
        if (sessionEntities.isEmpty()) emptyList<Session>()
        val speakers = speakersAsync.await()
        val links = linksAsync.await()
        sessionEntities
            .map { it.toSession(speakers, links) }
            .sortedWith(compareBy(
                { it.startTime },
                { it.room.name }
            ))
    }

    @WorkerThread
    override suspend fun getSession(id: Long): Session {
        TODO()
    }

    override suspend fun fetchSession() {
        val schedule = apiClient.fetchSession()
    }
}
