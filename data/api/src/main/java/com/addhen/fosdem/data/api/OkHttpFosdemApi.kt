package com.addhen.fosdem.data.api

import com.addhen.fosdem.platform.parser.Parser
import com.addhen.fosdem.platform.parser.Schedule
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpFosdemApi @Inject constructor(
    private val client: OkHttpClient,
    private val xmlParser: Parser<Schedule>
) : FosdemApi {

    private val requestBuilder: Request.Builder
        get() {
            return Request.Builder()
                .addHeader("Content-Type", "text/xml; charset=utf-8")
//                .addHeader("User-Agent", "Fosdem mobile app")
        }

    override suspend fun fetchSession(): Schedule {
        val request = requestBuilder
            .url(SCHEDULE_URL)
            .build()
        client.newCall(request).execute().use {
            if (it.isSuccessful) {
                return xmlParser.parse(it.body()!!.byteStream())
            }
            return Schedule(days = emptyList())
        }
    }

    companion object {

        private const val SCHEDULE_URL = "https://fosdem.org/schedule/xml"
    }
}
