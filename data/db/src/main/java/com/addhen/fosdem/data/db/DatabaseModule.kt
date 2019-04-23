package com.addhen.fosdem.data.db

import android.content.Context
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
object DatabaseModule {

    @JvmStatic
    @Provides
    @Singleton
    fun provideSession(
        context: Context
    ): SessionDatabase {
        return DatabaseComponent.builder()
            .context(context.applicationContext)
            .coroutineContext(Dispatchers.IO)
            .filename("fosdem.db")
            .build()
            .sessionDatabase()
    }
}
