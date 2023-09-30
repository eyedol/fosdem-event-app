// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class Event(
  val id: Long,
  val startTime: LocalTime,
  val duration: LocalTime,
  val title: String,
  val description: String,
  val abstractText: String,
  val isBookmarked: Boolean,
  val day: Day,
  val room: Room,
  val track: Track,
  val links: List<Link>,
  val speakers: List<Speaker>,
  val attachments: List<Attachment>,
)

val link = Link(
  id = 1,
  url = "https://link1.com",
  text = "Video recording1",
)

val speaker = Speaker(
  id = 1,
  name = "FOSDEM Staff",
)

val attachment = Attachment(
  id = 1,
  type = "slides",
  url = "https://attachment1.com",
  name = "Attachment 1",
)

val room = Room(
  id = 1,
  name = "Janson",
)

val link2 = Link(
  id = 2,
  url = "https://link2.com",
  text = "Video recording1",
)

val speaker2 = Speaker(
  id = 2,
  name = "Nick Vidal",
)

val attachment2 = Attachment(
  id = 2,
  type = "slides",
  url = "https://attachment2.com",
  name = "Attachment 2",
)

val room2 = Room(
  id = 2,
  name = "Janson",
)

val link3 = Link(
  id = 3,
  url = "https://link3.come",
  text = "FOSSi Foundation website",
)

val room3 = Room(
  id = 3,
  name = "K.1.105 (La Fontaine)",
)

val speaker3 = Speaker(
  id = 3,
  name = "Philipp Wagner",
)

val attachment3 = Attachment(
  id = 3,
  type = "presentation slides",
  url = "https://attachment3.com",
  name = "Attachment 3",
)

val day = Day(
  id = 1,
  date = LocalDate.parse("2023-02-04"),
)

val day2 = Day(
  id = 2,
  date = LocalDate.parse("2023-02-05"),
)

val day1Event = Event(
  id = 1,
  startTime = LocalTime.parse("09:30"),
  duration = LocalTime.parse("00:25"),
  title = "Celebrating 25 years of Open Source",
  description = "The open source software label was coined at a strategy session held on " +
    "February 3rd, 1998 in Palo Alto, California. That same month, the Open Source Initiative " +
    "(OSI) was founded as a general educational and advocacy organization to raise awareness and" +
    " adoption for the superiority of an open development process. One of the first tasks " +
    "undertaken by OSI was to draft the Open Source Definition (OSD). To this day, the OSD is " +
    "considered a gold standard of open-source licensing.\n" +
    "\n" +
    "In this session, we'll cover the rich and interconnected history of the Free Software and " +
    "Open Source movements, and demonstrate how, against all odds, open source has come " +
    "to \"win\" the world. But have we really won? Open source has always faced an " +
    "extraordinary uphill battle: from misinformation and FUD (Fear Uncertainty and Doubt) " +
    "constantly being spread by the most powerful corporations, to issues around sustainability " +
    "and inclusion.\n" +
    "\n" +
    "We'll navigate this rich history of open source and dive right into its future, exploring " +
    "the several challenges and opportunities ahead, including its key role on fostering " +
    "collaboration and innovation in emerging areas such as ML/AI and cybersecurity. " +
    "We'll share an interactive timeline during the presentation and throughout the year, " +
    "inviting the audience and the community at-large to share their open source stories and " +
    "dreams with each other.",
  isBookmarked = false,
  abstractText = "February 2023 marks the 25th Anniversary of Open Source. This is a huge " +
    "milestone for the whole community to celebrate! In this session, we'll travel back in " +
    "time to understand our rich journey so far, and look forward towards the future to " +
    "reimagine a new world where openness and collaboration prevail. Come along and celebrate " +
    "with us this very special moment",
  day = day,
  links = listOf(link, link2, link3),
  speakers = listOf(speaker, speaker2),
  room = room,
  track = Track("maintrack"),
  attachments = listOf(attachment, attachment2, attachment3),
)

val day2Event = Event(
  id = 2,
  startTime = LocalTime.parse("11:40"),
  duration = LocalTime.parse("00:15"),
  title = "An open source and open design educational robot",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSSbot is a free and open source and open design.",
  day = day2,
  links = listOf(link2),
  speakers = listOf(speaker2),
  room = room2,
  track = Track("keynote"),
  attachments = listOf(attachment2),
)
