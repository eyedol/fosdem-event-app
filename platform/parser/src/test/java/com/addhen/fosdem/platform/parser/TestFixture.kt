package com.addhen.fosdem.platform.parser

import com.addhen.fosdem.data.model.*
import com.addhen.fosdem.platform.extension.toDate
import java.util.*

internal object TestFixture {

    fun buildSchedule(): Schedule {
        val days = listOf(
            Schedule.Day(1, "2019-02-02".toDate(), buildDay1Schedule("2019-02-02".toDate())),
            Schedule.Day(2, "2019-02-03".toDate(), buildDay2Sessions("2019-02-03".toDate()))
        )
        return Schedule(days)
    }

    private fun buildDay1Schedule(currentDayDate: Date): List<Session> {
        val session1 = Session(
            id = 8967,
            startTime = resetCalendar(currentDayDate, "09:30").time,
            durationTime = resetCalendar(currentDayDate, "00:25").time,
            title = "Welcome to FOSDEM 2019",
            description = "",
            abstract = "",
            room = Room(name = "Janson", building = "J"),
            track = Track(name = "Keynotes", type = Track.Type.keynote),
            links = listOf(
                Link(
                    id = 0, href = "https://video.fosdem.org/2019/Janson/keynotes_welcome.webm",
                    text = "Video recording (WebM/VP9)"
                ),
                Link(
                    id = 0, href = "https://video.fosdem.org/2019/Janson/keynotes_welcome.mp4",
                    text = "Video recording (mp4)"
                ), Link(
                    id = 0, href = "https://submission.fosdem.org/feedback/8967.php",
                    text = "Submit feedback"
                )
            ), speakers = listOf(Speaker(id = 6, name = "FOSDEM Staff"))
        )

        val session2 = Session(
            id = 7545,
            startTime = resetCalendar(currentDayDate, "10:00").time,
            durationTime = resetCalendar(currentDayDate, "00:50").time,
            title = "Can Anyone Live in Full Software Freedom Today?",
            description = "", abstract = "", room = Room(name = "Janson", building = "J"),
            track = Track(name = "Keynotes", type = Track.Type.keynote),
            links = listOf(
                Link(
                    id = 0,
                    href = "https://video.fosdem.org/2019/Janson/full_software_freedom.webm",
                    text = "Video recording(WebM / VP9)"
                ),
                Link(
                    id = 0,
                    href = "https://video.fosdem.org/2019/Janson/full_software_freedom.mp4",
                    text = "Video recording(mp4)"
                ),
                Link(
                    id = 0,
                    href = "https://submission.fosdem.org/feedback/7545.php",
                    text = "Submit feedback"
                )
            ),
            speakers = listOf(
                Speaker(id = 441, name = "Bradley M . Kuhn"),
                Speaker(id = 448, name = "Karen Sandler")
            )
        )
        return listOf(session1, session2)
    }

    private fun buildDay2Sessions(currentDayDate: Date): List<Session> {
        val session1 = Session(
            id = 8776,
            startTime = resetCalendar(currentDayDate, "09:00").time,
            durationTime = resetCalendar(currentDayDate, "00:50").time,
            title = "Love What You Do, Everyday!",
            room = Room(name = "K.1.105(La Fontaine)", building = "K"),
            track = Track(name = "Miscellaneous", type = Track.Type.maintrack),
            links = listOf(
                Link(
                    id = 0,
                    href = "https://video.fosdem.org/2019/K.1.105/love_everyday.webm",
                    text = "Video recording(WebM / VP9)"
                ),
                Link(
                    id = 0,
                    href = "https://video.fosdem.org/2019/K.1.105/love_everyday.mp4",
                    text = "Video recording (mp4)"
                ),
                Link(
                    id = 0,
                    href = "https://submission.fosdem.org/feedback/8776.php",
                    text = "Submit feedback"
                )
            ),
            speakers = listOf(Speaker(id = 4907, name = "Zaheda Bhorat")),
            abstract = "",
            description = ""
        )

        val session2 = Session(
            id = 7349,
            startTime = resetCalendar(currentDayDate, "10:00").time,
            durationTime = resetCalendar(currentDayDate, "00:50").time,
            title = "Tesla Hacking to FreedomEV!",
            description = "", abstract = "",
            room = Room(name = "K.1.105(La Fontaine)", building = "K"),
            track = Track(name = "Hardware", type = Track.Type.maintrack),
            links = listOf(
                Link(
                    id = 0,
                    href = "http://www.freedomev.com",
                    text = "The to be Fosdem-launched FreedomEV website"
                ),
                Link
                    (
                    id = 0,
                    href = "http://www.freedomev.com/wiki/index.php?title=Main_Page",
                    text = "FreedomEV Wiki"
                ),
                Link
                    (
                    id = 0,
                    href = "https://github.com/jnuyens/freedomev",
                    text = "FreedomEV Github"
                ),
                Link
                    (
                    id = 0,
                    href = "https://video.fosdem.org/2019/K.1.105/tesla_hacking.webm",
                    text = "Video recording (WebM/VP9)"
                ),
                Link
                    (
                    id = 0,
                    href = "https://video.fosdem.org/2019/K.1.105/tesla_hacking.mp4",
                    text = "Video recording (mp4)"
                ),
                Link
                    (
                    id = 0,
                    href = "https://submission.fosdem.org/feedback/7349.php",
                    text = "Submit feedback"
                )
            ),
            speakers = listOf(Speaker(id = 5425, name = "Jasper Nuyens"))
        )

        return listOf(session1, session2)
    }

    private fun resetCalendar(currentDayDate: Date, time: String): Calendar {
        val calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.US)
        calendar.time = currentDayDate
        calendar.set(Calendar.HOUR_OF_DAY, getHours(time))
        calendar.set(Calendar.MINUTE, getMinutes(time))
        return calendar
    }

    private fun getHours(time: String): Int {
        return Character.getNumericValue(time[0]) * 10 + Character.getNumericValue(time[1])
    }

    private fun getMinutes(time: String): Int {
        return Character.getNumericValue(time[3]) * 10 + Character.getNumericValue(time[4])
    }
}
