package com.addhen.fosdem.data.events.di

import com.addhen.fosdem.data.events.api.KtorEventsApi
import com.addhen.fosdem.data.events.api.api.EventsApi
import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.events.database.EventsDbDao
import com.addhen.fosdem.data.events.repository.EventsDataRepository
import me.tatarka.inject.annotations.Provides

interface EventsDataBinds {
  @Provides
  fun providesEventsApi(bind: KtorEventsApi): EventsApi = bind

  @Provides
  fun providesEventsRepository(bind: EventsDataRepository): EventsRepository = bind

  @Provides
  fun providesEventsDao(bind: EventsDbDao): EventsDao = bind
}
