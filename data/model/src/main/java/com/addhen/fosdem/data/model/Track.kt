package com.addhen.fosdem.data.model

data class Track(val name: String, val type: Type) {

    enum class Type(val rgb: Int, val displayName: String) {
        other(0x757575, "Other"),
        keynote(0x76005E, "Keynote"),
        maintrack(0xD32F2F, "Main Track"),
        devroom(0x303F9F, "Dev Room"),
        lightningtalk(0x388E3C, "Lightning Talk"),
        certification(0x795548, "Certification")
    }
}

