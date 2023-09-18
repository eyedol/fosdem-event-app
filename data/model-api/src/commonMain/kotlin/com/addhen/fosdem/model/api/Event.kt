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
  name = "attachment 1",
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
  name = "attachment 2",
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
  name = "attachment 3",
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
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk",
  day = day,
  links = listOf(link),
  speakers = listOf(speaker),
  room = room,
  track = Track("Keynotes", Track.Type.BOF),
  attachments = listOf(attachment),
)
