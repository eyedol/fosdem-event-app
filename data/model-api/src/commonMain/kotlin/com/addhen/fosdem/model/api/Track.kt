package com.addhen.fosdem.model.api

data class Track(
  val name: String,
  val type: Type
){
  enum class Type(rgb: Int, displayName: String) {
    other(0x757575, "Other"),
    keynote(0x76005E, "Keynote"),
    maintrack(0xD32F2F, "Main Track"),
    devroom(0x303F9F, "Dev Room"),
    lightningtalk(0x388E3C, "Lightning Talk"),
    certification(0x795548, "Certification"),
    bof(0x795548, "BoF"),
    workshop(0x795548, "Workshop"),
    keysigning(0x303F9F, "Keysigning")
  }
}
