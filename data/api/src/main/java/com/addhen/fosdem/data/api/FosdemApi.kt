package com.addhen.fosdem.data.api

import com.addhen.fosdem.platform.parser.Schedule


interface FosdemApi {

    suspend fun fetchSession(): Schedule
}
