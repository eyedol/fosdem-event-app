package com.addhen.fosdem.data.db.room

import com.addhen.fosdem.data.db.SessionDatabase
import com.addhen.fosdem.data.db.room.dao.LinkDao
import com.addhen.fosdem.data.db.room.dao.SessionSpeakerLinkJoinDao
import com.addhen.fosdem.data.db.room.dao.SpeakerDao
import com.addhen.fosdem.data.db.room.entity.LinkEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerLinkJoinEntity
import com.addhen.fosdem.data.db.room.entity.SpeakerEntity
import javax.inject.Inject

class RoomDatabase @Inject constructor(
    private val sessionSpeakerLinkDao: SessionSpeakerLinkJoinDao,
    private val speakerDao: SpeakerDao,
    private val linkDao: LinkDao
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
}
