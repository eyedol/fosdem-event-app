package com.addhen.fosdem.data.db.room.dao

import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.room.*
import com.addhen.fosdem.data.db.room.entity.SessionEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerLinkJoinEntity
import org.intellij.lang.annotations.Language

@Dao
abstract class SessionDao {

    @Language("RoomSql")
    @Query("SELECT * FROM session")
    abstract fun sessionsLiveData(): LiveData<List<SessionEntity>>

    @Query("SELECT * FROM session")
    abstract fun sessions(): List<SessionEntity>

    @Language("RoomSql")
    @Transaction
    @CheckResult
    @Query("SELECT * FROM session")
    abstract suspend fun sessionWithSpeakersAndLinks(): List<SessionSpeakerLinkJoinEntity>

    @Query("DELETE FROM session")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(sessions: List<SessionEntity>)

    @Transaction
    open fun add(sessions: List<SessionEntity>) {
        deleteAll()
        insert(sessions)
    }
}
