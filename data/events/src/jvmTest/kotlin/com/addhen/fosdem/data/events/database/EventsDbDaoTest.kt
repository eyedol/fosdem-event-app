package com.addhen.fosdem.data.events.database

import com.addhen.fosdem.data.events.api.database.EventsDao
import com.addhen.fosdem.test.CoroutineTestRule
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class EventsDbDaoTest: DatabaseTest() {
  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: EventsDao

  @BeforeEach
  fun set() {
    sut = EventsDbDao(clock = Clock.System, TimeZone.UTC, database, coroutineTestRule.testDispatcherProvider)
  }

  @Test
  fun `successfully gets events by day from the database`() = coroutineTestRule.runTest {

  }
}
