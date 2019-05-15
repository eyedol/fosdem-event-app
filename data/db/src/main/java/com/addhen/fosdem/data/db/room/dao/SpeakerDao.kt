package com.addhen.fosdem.data.db.room.dao

import androidx.room.*
import com.addhen.fosdem.data.db.room.entity.SpeakerEntity

@Dao
abstract class SpeakerDao {

    @Query("SELECT * FROM speaker")
    abstract suspend fun getAllSpeakers(): List<SpeakerEntity>

    @Query("DELETE FROM speaker")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(speakers: List<SpeakerEntity>)

    @Transaction
    open fun add(speakers: List<SpeakerEntity>) {
        deleteAll()
        insert(speakers)
    }
}
