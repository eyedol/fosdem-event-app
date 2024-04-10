// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

import com.addhen.fosdem.core.api.plus
import com.addhen.fosdem.core.api.toLocalDateTime
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Event(
  val id: Long,
  val startAt: LocalTime,
  val endAt: LocalTime,
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
  startAt = LocalTime.parse("09:30"),
  endAt = LocalTime.parse("09:30") + LocalTime.parse("00:25"),
  duration = LocalTime.parse("00:25"),
  title = "Welcome to FOSDEM 2023",
  description = "Welcome to FOSDEM 2023!",
  isBookmarked = false,
  abstractText = "FOSDEM welcome and opening talk.",
  day = day,
  links = listOf(link, link2, link3),
  speakers = listOf(speaker, speaker2),
  room = room,
  track = Track("Keynote", "keynote"),
  attachments = listOf(attachment, attachment2, attachment3),
)

val day1Event2 = Event(
  id = 2,
  startAt = LocalTime.parse("10:00"),
  endAt = LocalTime.parse("10:00") + LocalTime.parse("00:50"),
  duration = LocalTime.parse("00:50"),
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
  track = Track("Main Track", "maintrack"),
  attachments = listOf(attachment, attachment2, attachment3),
)

val day2Event1 = Event(
  id = 3,
  startAt = LocalTime.parse("09:00"),
  endAt = LocalTime.parse("09:00") + LocalTime.parse("00:50"),
  duration = LocalTime.parse("00:50"),
  title = "Open Source in Environmental Sustainability",
  description = "",
  isBookmarked = false,
  abstractText = "The transition to a more sustainable future requires not " +
    "only technological innovation, but also new opportunities for society " +
    "to participate in the development and adoption of technologies. " +
    "Open source culture has demonstrated how transparent and collaborative " +
    "innovation can support modern digital services, data and infrastructure." +
    " Open Source Software (OSS) accelerates the transition to a sustainable" +
    " economy by supporting traceable decision-making, building capacity " +
    "for localisation and customisation of climate technologies, and " +
    "most importantly, helping to prevent greenwashing. Despite the " +
    "transformative impact of open source culture, its potential for " +
    "developing environmentally sustainable technologies is not well " +
    "understood.&lt;/p&gt;\n" +
    "\n" +
    "This study provides the first analysis of the open source software " +
    "ecosystem in the field of sustainability and climate technology. " +
    "Thousands of actively developed open source projects and " +
    "organizations were collected and systematically analyzed using " +
    "qualitative and quantitative methods as part of the Open Sustainable " +
    "Technology project. The analysis covers multiple dimensions – including " +
    "the technical, the social, and the organisational. It highlights key " +
    "risks and challenges for users, developers, and decision-makers as well " +
    "as opportunities for more systemic collaboration. Based on these unique " +
    "insights, we were also able to define the Open Sustainability Principles that " +
    "embody open source in sustainability.",
  day = day2,
  links = listOf(link2),
  speakers = listOf(speaker2),
  room = room2,
  track = Track("Main Track", "maintrack"),
  attachments = listOf(attachment2),
)

val day2Event2 = Event(
  id = 4,
  startAt = LocalTime.parse("10:00"),
  duration = LocalTime.parse("00:50"),
  endAt = LocalTime.parse("10:00") + LocalTime.parse("00:50"),
  title = "Making the world a better place through Open Source",
  description = "If software is eating the world, then, by all metrics, Open Source is eating " +
    "software. That means that collaborations and innovation that happen every day in the open " +
    "source community don't only have a major impact on technology but, as every industry and " +
    "nation states undergo the \"digital transformation\", have a unique potential to make a " +
    "lasting impact in our daily lives and offer novel solutions to long standing issues like " +
    "climate change, incurable illnesses, financial inclusion and food scarcity, as well as " +
    "bridge historical geopolitical rifts to continue to deepen in an increasingly divided " +
    "world.\n" +
    "\n" +
    "This session will provide an overview of existing open source projects which are making " +
    "already a huge impact on fundamental areas like Energy and Climate, Public Healthcare, " +
    "Agricultural innovation and financial inclusion, as well as hone in on areas where open " +
    "source collaboration can build bridges across historically divided, if not hostile, " +
    "regions and states by putting forward a model for a positive sum-game that all actors in " +
    "the open source community, from individuals to the private and public sector, can benefit " +
    "from, while delivering immense collective value.\n" +
    "\n" +
    "Developers and especially open source contributors have a unique power in their hands " +
    "if we collectively are able to harness it towards higher and higher order challenges: " +
    "this talk will be an open letter and call to action to everyone, with a particular focus " +
    "on policy makers and governments around the world, to focus our efforts towards making " +
    "the world a better place through open collaboration.",
  isBookmarked = false,
  abstractText = "In a world characterized by an increasingly complex geopolitical climate, " +
    "war and with vital challenges like climate change begging for immediate and substantial " +
    "action, the open source community has a unique role to play and has a vital chance to " +
    "deliver solutions for these long standing issues at a pace and effectiveness that no " +
    "single individual or public or private entity could.\n" +
    "\n" +
    "In this session we will explore how contributors, maintainers, public and private " +
    "sector are, should come together through the positive-sum game that open source is to " +
    "impact not only the future of technology but drive impactful outcomes is some of the " +
    "most pressing global social challenges.",
  day = day2,
  links = listOf(link2),
  speakers = listOf(speaker2, speaker3),
  room = room2,
  track = Track("Main Track", "maintrack"),
  attachments = listOf(attachment2),
)

val day2Event3 = Event(
  id = 5,
  startAt = LocalTime.parse("10:00"),
  endAt = LocalTime.parse("10:00") + LocalTime.parse("00:50"),
  duration = LocalTime.parse("00:50"),
  title = "Building Strong Foundations for a More Secure Future",
  description = "",
  isBookmarked = true,
  abstractText = "The open source community has become vulnerable to new kinds of attacks on " +
    "the software supply chain and there have been efforts by many to address those challenges. " +
    "Those efforts require new processes, new tools, and new initiatives to drive adoption. " +
    "Heightened interest, particularly by governments of the world, has driven the open source " +
    "community to respond with a mobilization plan to achieve specific goals. The Linux " +
    "Foundation " +
    "and OpenSSF delivered a first-of-its-kind plan to broadly address open source and software " +
    "supply chain security outlining approximately \$150M of funding over two years to rapidly " +
    "advance well-vetted solutions to the ten major problems facing open source software " +
    "security. " +
    "These concrete action steps are designed to produce immediate improvements and build strong " +
    "foundations for a more secure future. Find out what you can do to be more secure and " +
    "support " +
    "this global security effort.",
  day = day2,
  links = listOf(link2),
  speakers = listOf(speaker2, speaker3),
  room = room2,
  track = Track("Main Track", "maintrack"),
  attachments = listOf(attachment2),
)

fun List<Event>.sortAndGroupedEventsItems() =
  groupBy {
    it.startAtLocalDateTime.toString() + it.endAtLocalDateTime.toString()
  }
    .mapValues { entries ->
      entries.value.sortedWith(
        compareBy(
          { it.day.date.toString() },
          { it.startAtLocalDateTime.toString() },
        ),
      )
    }.sortMapByKey().toPersistentMap()

fun <K : Comparable<K>, V> Map<out K, V>.sortMapByKey(): Map<K, V> {
  return this.toList().sortedBy { it.first }.toMap()
}

val Event.startAtLocalDateTime: LocalDateTime
  get() = this.startAt.toLocalDateTime(day.date)

val Event.endAtLocalDateTime: LocalDateTime
  get() = this.endAt.toLocalDateTime(day.date)

val Event.descriptionFullText: String
  get() {
    return when {
      description.isBlank().not() && abstractText.isBlank().not() -> {
        "${abstractText}\n\n$description"
      }
      description.isBlank().not() -> description
      else -> abstractText
    }
  }
