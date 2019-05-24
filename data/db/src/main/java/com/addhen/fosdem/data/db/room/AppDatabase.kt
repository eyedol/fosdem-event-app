package com.addhen.fosdem.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.addhen.fosdem.data.db.room.dao.LinkDao
import com.addhen.fosdem.data.db.room.dao.SessionDao
import com.addhen.fosdem.data.db.room.dao.SpeakerDao
import com.addhen.fosdem.data.db.room.entity.LinkEntity
import com.addhen.fosdem.data.db.room.entity.SessionEntity
import com.addhen.fosdem.data.db.room.entity.SpeakerEntity

@Database(
    entities = [
        (SessionEntity::class),
        (SpeakerEntity::class),
        (LinkEntity::class)
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao
    abstract fun speakerDao(): SpeakerDao
    abstract fun linkDao(): LinkDao
}
