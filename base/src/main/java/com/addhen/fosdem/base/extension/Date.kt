package com.addhen.fosdem.base.extension

import java.text.SimpleDateFormat
import java.util.Date

fun Date.toTitle(): String {

    return SimpleDateFormat("dd.MM.y").format(this)
}
