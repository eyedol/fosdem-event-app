package com.addhen.fosdem.platform.parser

import com.addhen.fosdem.data.model.Session
import java.util.*

data class Schedule(val days: List<Day>) {

    data class Day(val index: Int, val date: Date, val events: List<Session>)
}
