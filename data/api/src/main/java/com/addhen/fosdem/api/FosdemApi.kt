package com.addhen.fosdem.api

import com.addhen.fosdem.platform.parser.Schedule


interface FosdemApi {

    suspend fun getSessions(): Schedule
}
