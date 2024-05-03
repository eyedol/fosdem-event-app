// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search

import com.addhen.fosdem.core.api.screens.SessionsScreen
import com.addhen.fosdem.data.events.api.repository.EventsRepository
import com.addhen.fosdem.data.rooms.api.repository.RoomsRepository
import com.addhen.fosdem.model.api.Room
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.fake.event.FakeEventsRepository
import com.slack.circuit.test.FakeNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.extension.RegisterExtension
import java.util.concurrent.atomic.AtomicBoolean

@OptIn(ExperimentalCoroutinesApi::class)
class SessionSearchPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(SessionsScreen)
  private val fakeRepository = FakeEventsRepository()
  private val fakeRoomsRepository = FakeRoomsRepository()

  private val eventsRepository: Lazy<EventsRepository>
    get() = lazy { fakeRepository }

  private val roomRepository: Lazy<RoomsRepository>
    get() = lazy { fakeRoomsRepository }

  private val sut: SessionSearchPresenter = SessionSearchPresenter(
    navigator = navigator,
    roomsRepository = roomRepository,
    eventsRepository = eventsRepository,
  )

  internal class FakeRoomsRepository : RoomsRepository {
    val shouldCauseAnError: AtomicBoolean = AtomicBoolean(false)
    private val rooms = mutableListOf<Room>()

    override fun getRooms() = flow {
      if (shouldCauseAnError.get()) {
        throw RuntimeException("Error occurred while getting rooms")
      }
      emit(rooms.toList())
    }

    fun addRooms(vararg room: Room) {
      rooms.addAll(room)
    }

    fun clearRooms() {
      rooms.clear()
      shouldCauseAnError.set(false)
    }
  }
}
