// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.api.api.dto

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import nl.adaptivity.xmlutil.serialization.XmlChildrenName
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName("schedule")
data class EventDto(
  val days: List<Days>,
) {
  @Serializable
  @XmlSerialName("day")
  data class Days(
    val index: Int,
    @Serializable(LocalDateSerializer::class) val date: LocalDate,
    val rooms: List<Rooms>,
  ) {
    @Serializable
    @XmlSerialName("room")
    data class Rooms(val name: String, val events: List<Event>) {
      @Serializable
      @XmlSerialName("event")
      data class Event(
        val id: Long,
        @XmlElement(true)
        @Serializable(LocalTimeSerializer::class)
        val start: LocalTime,
        @XmlElement(true)
        @Serializable(LocalTimeSerializer::class)
        val duration: LocalTime,
        @XmlElement(true)
        val title: String,
        @XmlElement(true)
        val description: String,
        @XmlElement(true)
        val abstract: String,
        @XmlElement(true)
        val track: String,
        @XmlElement(true)
        val type: String,
        @XmlChildrenName("link")
        val links: List<Link>,
        @XmlChildrenName("person")
        val persons: List<Speaker>,
        @XmlChildrenName("attachment")
        val attachments: List<Attachment>,
      ) {
        @Serializable
        @XmlSerialName("link")
        data class Link(
          val href: String,
          @XmlValue(true)
          val text: String,
        )

        @Serializable
        @XmlSerialName("attachment")
        data class Attachment(
          val type: String,
          val href: String,
          @XmlValue(true)
          val name: String?,
        )

        @Serializable
        data class Speaker(val id: Long, @XmlValue(true) val name: String)
      }
    }
  }
}

internal object LocalTimeSerializer : KSerializer<LocalTime> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): LocalTime = LocalTime.parse(decoder.decodeString())

  override fun serialize(encoder: Encoder, value: LocalTime) {
    encoder.encodeString(value.toString())
  }
}

internal object LocalDateSerializer : KSerializer<LocalDate> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): LocalDate = LocalDate.parse(decoder.decodeString())

  override fun serialize(encoder: Encoder, value: LocalDate) {
    encoder.encodeString(value.toString())
  }
}
