package com.addhen.fosdem.data.db

import android.content.Context
import com.addhen.fosdem.data.db.room.RoomDatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
@Component(
    modules = [
        RoomDatabaseModule::class
    ]
)
interface DatabaseComponent {
    fun sessionDatabase(): SessionDatabase

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun coroutineContext(coroutineContext: CoroutineContext): Builder

        @BindsInstance
        fun filename(filename: String?): Builder

        fun build(): DatabaseComponent
    }

    companion object {
        fun builder(): Builder = DaggerDatabaseComponent.builder()
    }
}
