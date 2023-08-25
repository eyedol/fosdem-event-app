package com.addhen.fosdem.data.events.api

import com.addhen.fosdem.core.api.AppError
import com.addhen.fosdem.core.api.network.ApiService
import com.addhen.fosdem.test.CoroutineTestRule
import com.addhen.fosdem.test.createHttpClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.RegisterExtension

class KtorEventsApiTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var sut: KtorEventsApi

  @Test
  fun `successfully fetches and deserializes to event dto`() = coroutineTestRule.runTest {
    val httpClient = createHttpClient(
      response = scheduleXML,
    )
    val api = ApiService("https://mock.api.com", httpClient)
    sut = KtorEventsApi(api, coroutineTestRule.testDispatcherProvider)

    val actual = sut.fetchEvents()

    assertEquals(1, actual.days.size)
    assertEquals(1, actual.days.first().rooms.size)
    assertEquals(2, actual.days.first().rooms.first().events.size)
  }

  @Test
  fun `throws an exception as fetching  schedules as there is malformed xml`() = coroutineTestRule.runTest {
    val httpClient = createHttpClient(
      response = """
        <?xml version="1.0" encoding="UTF-8"?>
  <schedule>
    <conference>
    <title>FOSDEM 2023</title>
    <subtitle/>
    <venue>ULB (Université Libre de Bruxelles)</venue>
    <city>Brussels</city>
    <start>2023-02-04</start>
      """.trimIndent(),
    )
    val api = ApiService("https://mock.api.com", httpClient)
    sut = KtorEventsApi(api, coroutineTestRule.testDispatcherProvider)

    assertThrows<AppError.UnknownException> {
      sut.fetchEvents()
    }
  }

  private val scheduleXML = """
  <?xml version="1.0" encoding="UTF-8"?>
  <schedule>
    <conference>
    <title>FOSDEM 2023</title>
    <subtitle/>
    <venue>ULB (Université Libre de Bruxelles)</venue>
    <city>Brussels</city>
    <start>2023-02-04</start>
    <end>2023-02-05</end>
    <days>2</days>
    <day_change>09:00:00</day_change>
    <timeslot_duration>00:05:00</timeslot_duration>
    </conference>
    <day index="1" date="2023-02-04">
    <room name="Janson">
    <event id="15059">
    <start>09:30</start>
    <duration>00:25</duration>
    <room>Janson</room>
    <slug>keynotes_welcome</slug>
    <title>Welcome to FOSDEM 2023</title>
    <subtitle/>
    <track>Keynotes</track>
    <type>keynote</type>
    <language/>
    <abstract>&lt;p&gt;FOSDEM welcome and opening talk.&lt;/p&gt;</abstract>
    <description>&lt;p&gt;Welcome to FOSDEM 2023!&lt;/p&gt;</description>
    <persons>
    <person id="6">FOSDEM Staff</person>
    <person id="497">Richard Hartmann</person>
    </persons>
    <attachments>
    <attachment type="slides" href="https://fosdem.org/2023/schedule/event/keynotes_welcome/attachments/slides/5986/export/events/attachments/keynotes_welcome/slides/5986/2023_02_04_FOSDEM_Welcome_opening_talk.pdf">Slides</attachment>
    </attachments>
    <links>
    <link href="https://chat.fosdem.org/#/room/#2023-janson:fosdem.org">Chat room (web)</link>
    <link href="https://matrix.to/#/#2023-janson:fosdem.org?web-instance[element.io]=chat.fosdem.org">Chat room (app)</link>
    <link href="https://submission.fosdem.org/feedback/15059.php">Submit feedback</link>
    </links>
    </event>
    <event id="14956">
        <start>10:00</start>
        <duration>00:50</duration>
        <room>Janson</room>
        <slug>celebrating_25_years_of_open_source</slug>
        <title>Celebrating 25 years of Open Source</title>
        <subtitle>Past, Present, and Future</subtitle>
        <track>Keynotes</track>
        <type>keynote</type>
        <language/>
        <abstract>&lt;p&gt;February 2023 marks the 25th Anniversary of Open Source. This is a huge milestone for the whole community to celebrate! In this session, we'll travel back in time to understand our rich journey so far, and look forward towards the future to reimagine a new world where openness and collaboration prevail. Come along and celebrate with us this very special moment!&lt;/p&gt;</abstract>
        <description>&lt;p&gt;The open source software label was coined at a strategy session held on February 3rd, 1998 in Palo Alto, California. That same month, the Open Source Initiative (OSI) was founded as a general educational and advocacy organization to raise awareness and adoption for the superiority of an open development process. One of the first tasks undertaken by OSI was to draft the Open Source Definition (OSD). To this day, the OSD is considered a gold standard of open-source licensing.&lt;/p&gt;Well share an interactive timeline during the presentation and throughout the year, inviting the audience and the community atlarge to share their open source stories and dreams with each other.</description>
        <persons>
          <person id="9505">Nick Vidal</person>
        </persons>
        <attachments>
        </attachments>
        <links>
          <link href="https://anniv.co">Open Anniversary</link>
          <link href="https://video.fosdem.org/2023/Janson/celebrating_25_years_of_open_source.webm">Video recording (WebM/VP9, 101M)</link>
          <link href="https://video.fosdem.org/2023/Janson/celebrating_25_years_of_open_source.mp4">Video recording (mp4/aac, 249M)</link>
          <link href="https://chat.fosdem.org/#/room/#2023-janson:fosdem.org">Chat room (web)</link>
          <link href="https://matrix.to/#/#2023-janson:fosdem.org?web-instance[element.io]=chat.fosdem.org">Chat room (app)</link>
          <link href="https://submission.fosdem.org/feedback/14956.php">Submit feedback</link>
        </links>
      </event>
    </room>
    </day>
  </schedule>
  """.trimIndent()
}
