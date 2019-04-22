package com.addhen.fosdem.data.db

import com.addhen.fosdem.data.db.room.entity.SessionEntity

interface SessionDatabase {
    suspend fun sessions(): List<SessionEntity>
}
