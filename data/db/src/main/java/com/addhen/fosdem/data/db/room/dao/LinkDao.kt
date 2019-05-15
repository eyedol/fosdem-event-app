package com.addhen.fosdem.data.db.room.dao

import androidx.room.*
import com.addhen.fosdem.data.db.room.entity.LinkEntity

@Dao
abstract class LinkDao {

    @Query("SELECT * FROM link")
    abstract suspend fun getAllLinks(): List<LinkEntity>

    @Query("DELETE FROM link")
    abstract fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(links: List<LinkEntity>)

    @Transaction
    open fun add(links: List<LinkEntity>) {
        deleteAll()
        insert(links)
    }
}
