package com.addhen.fosdem.data.db.room

import android.content.Context
import androidx.room.Room
import com.addhen.fosdem.data.db.SessionDatabase
import com.addhen.fosdem.data.db.room.dao.LinkDao
import com.addhen.fosdem.data.db.room.dao.SessionDao
import com.addhen.fosdem.data.db.room.dao.SessionSpeakerLinkJoinDao
import com.addhen.fosdem.data.db.room.dao.SpeakerDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal object RoomDatabaseModule {

    @Provides
    @Singleton
    @JvmStatic
    fun sessionDatabase(roomDatabase: RoomDatabase): SessionDatabase {
        return roomDatabase
    }

    @Provides
    @Singleton
    @JvmStatic
    fun database(context: Context, filename: String?): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, filename ?: "fosdem.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @JvmStatic
    @Provides
    fun sessionDao(database: AppDatabase): SessionDao {
        return database.sessionDao()
    }

    @JvmStatic
    @Provides
    fun speakerDao(database: AppDatabase): SpeakerDao {
        return database.speakerDao()
    }

    @JvmStatic
    @Provides
    fun sessionSpeakerJoinDao(database: AppDatabase): SessionSpeakerLinkJoinDao {
        return database.sessionSpeakerLinkJoinDao()
    }

    @JvmStatic
    @Provides
    fun linkDao(database: AppDatabase): LinkDao {
        return database.linkDao()
    }
}
