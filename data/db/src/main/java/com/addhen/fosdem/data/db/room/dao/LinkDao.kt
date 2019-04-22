package com.addhen.fosdem.data.db.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.addhen.fosdem.data.db.room.entity.LinkEntity

@Dao
abstract class LinkDao {

    @Query("SELECT * FROM link")
    abstract suspend fun getAllLinks(): List<LinkEntity>

    @Query("DELETE FROM link")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(speakers: List<LinkEntity>)
}
