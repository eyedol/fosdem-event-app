package com.addhen.fosdem.data.db.room.dao

import androidx.annotation.CheckResult
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.addhen.fosdem.data.db.room.entity.SessionLinkJoinEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerJoinEntity
import com.addhen.fosdem.data.db.room.entity.SessionSpeakerLinkJoinEntity
import org.intellij.lang.annotations.Language

@Dao
abstract class SessionSpeakerLinkJoinDao {
    @Language("RoomSql")
    @Transaction
    @CheckResult
    @Query("SELECT * FROM session")
    abstract suspend fun getAllSessions():
        List<SessionSpeakerLinkJoinEntity>

    @Insert
    abstract fun insertSessionWithSpeaker(sessionSpeakerJoin: List<SessionSpeakerJoinEntity>)

    @Insert
    abstract fun insertSessionWithLink(sessionLinkJoin: List<SessionLinkJoinEntity>)

    @Query("DELETE FROM session_speaker_join")
    abstract fun deleteSessionSpeakerJoinAll()

    @Query("DELETE FROM session_link_join")
    abstract fun deleteSessionLinkJoinAll()

    @Transaction
    fun addSessionSpeakerJoin(sessionSpeakerJoin: List<SessionSpeakerJoinEntity>) {
        deleteSessionSpeakerJoinAll()
        insertSessionWithSpeaker(sessionSpeakerJoin)
    }

    @Transaction
    fun addSessionLinkJoin(sessionLinkJoin: List<SessionLinkJoinEntity>) {
        deleteSessionLinkJoinAll()
        insertSessionWithLink(sessionLinkJoin)
    }
}
