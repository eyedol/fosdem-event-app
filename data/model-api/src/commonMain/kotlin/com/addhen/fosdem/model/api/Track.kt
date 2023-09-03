// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

data class Track(
  val name: String,
  val type: Type,
) {
  enum class Type(rgb: Int, displayName: String) {
    OTHER(0x757575, "Other"),
    KEYNOTE(0x76005E, "Keynote"),
    MAIN_TRACK(0xD32F2F, "Main Track"),
    DEV_ROOM(0x303F9F, "devroom"),
    LIGHTING_TALK(0x388E3C, "lightningtalk"),
    CERTIFICATION(0x795548, "Certification"),
    BOF(0x795548, "BoF"),
    WORKSHOP(0x795548, "Workshop"),
    KEYSIGNING(0x303F9F, "Keysigning"),
  }
}
