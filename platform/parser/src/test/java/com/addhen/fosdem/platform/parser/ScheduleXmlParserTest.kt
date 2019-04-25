package com.addhen.fosdem.platform.parser

import com.addhen.fosdem.platform.extension.toDate
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.kxml2.io.KXmlParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

@RunWith(JUnit4::class)
class ScheduleXmlParserTest {

    private val scheduleXmlParser = ScheduleXmlParser(KXmlParser())

    @Test
    @Throws(XmlPullParserException::class, IOException::class)
    fun `should parse schedule xml file with no issue`() {
        val expectedSchedule = buildSchedule()
        val inputStream = TestHelper.getStringFromFile("schedules.xml")

        val actualSchedule = scheduleXmlParser.parse(inputStream)

        assertEquals(expectedSchedule, actualSchedule)
    }

    private fun buildSchedule(): Schedule {
        val days = listOf(
            Schedule.Day(1, "2019-02-02".toDate(), listOf()),
            Schedule.Day(2, "2019-02-03".toDate(), listOf())
        )
        return Schedule(days)
    }
}
