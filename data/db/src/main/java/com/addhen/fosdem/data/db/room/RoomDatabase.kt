package com.addhen.fosdem.data.db.room

import com.addhen.fosdem.data.db.SessionDatabase
import com.addhen.fosdem.data.db.room.dao.LinkDao
import com.addhen.fosdem.data.db.room.dao.SessionDao
import com.addhen.fosdem.data.db.room.dao.SessionSpeakerLinkJoinDao
import com.addhen.fosdem.data.db.room.dao.SpeakerDao
import com.addhen.fosdem.data.db.room.entity.LinkEntity
import com.addhen.fosdem.data.db.room.entity.SessionEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerLinkJoinEntity
import com.addhen.fosdem.data.db.room.entity.SpeakerEntity
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RoomDatabase @Inject constructor(
    private val sessionSpeakerLinkDao: SessionSpeakerLinkJoinDao,
    private val sessionDao: SessionDao,
    private val speakerDao: SpeakerDao,
    private val linkDao: LinkDao,
    private val coroutineContext: CoroutineContext
) : SessionDatabase {

    override suspend fun speakers(): List<SpeakerEntity> {
        return speakerDao.getAllSpeakers()
    }

    override suspend fun links(): List<LinkEntity> {
        return linkDao.getAllLinks()
    }

    override suspend fun sessions(): List<SessionSpeakerLinkJoinEntity> {
        return sessionSpeakerLinkDao.getAllSessions()
    }

    override suspend fun save(
        sessions: List<SessionEntity>,
        links: List<LinkEntity>,
        speakers: List<SpeakerEntity>
    ) {
        withContext(coroutineContext) {
            speakerDao.add(speakers)
            linkDao.add(links)
            sessionDao.add(sessions)
        }
    }
}
