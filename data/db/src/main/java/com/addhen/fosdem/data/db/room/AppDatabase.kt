package com.addhen.fosdem.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.addhen.fosdem.data.db.room.dao.LinkDao
import com.addhen.fosdem.data.db.room.dao.SessionDao
import com.addhen.fosdem.data.db.room.dao.SessionSpeakerLinkJoinDao
import com.addhen.fosdem.data.db.room.dao.SpeakerDao
import com.addhen.fosdem.data.db.room.entity.*

@Database(
    entities = [
        (SessionEntity::class),
        (SpeakerEntity::class),
        (SessionSpeakerJoinEntity::class),
        (SessionLinkJoinEntity::class),
        (LinkEntity::class)
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao
    abstract fun linkDao(): LinkDao
    abstract fun sessionSpeakerLinkJoinDao(): SessionSpeakerLinkJoinDao
}
