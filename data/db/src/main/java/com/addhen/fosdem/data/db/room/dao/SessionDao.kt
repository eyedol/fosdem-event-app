package com.addhen.fosdem.data.db.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.addhen.fosdem.data.db.room.entity.SessionEntity

@Dao
abstract class SessionDao {
    @Query("SELECT * FROM session")
    abstract fun sessionsLiveData(): LiveData<List<SessionEntity>>

    @Query("SELECT * FROM session")
    abstract fun sessions(): List<SessionEntity>

    @Query("DELETE FROM session")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(sessions: List<SessionEntity>)
}
