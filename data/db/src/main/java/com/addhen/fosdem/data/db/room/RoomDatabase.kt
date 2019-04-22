package com.addhen.fosdem.data.db.room

import com.addhen.fosdem.data.db.SessionDatabase
import com.addhen.fosdem.data.db.room.entity.SessionEntity
import javax.inject.Inject

class RoomDatabase @Inject constructor() : SessionDatabase {
    
    override suspend fun sessions(): List<SessionEntity> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
