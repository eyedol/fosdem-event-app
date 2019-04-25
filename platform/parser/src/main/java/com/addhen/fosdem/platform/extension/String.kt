package com.addhen.fosdem.platform.extension

import java.text.SimpleDateFormat
import java.util.*

fun String.toDate(): Date {
    // TODO localize this to timezone
    return SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(this)
}
