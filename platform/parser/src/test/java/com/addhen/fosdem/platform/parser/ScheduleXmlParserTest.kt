package com.addhen.fosdem.platform.parser

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
        val expectedSchedule = TestFixture.buildSchedule()
        val inputStream = TestHelper.getStringFromFile("schedules.xml")

        val actualSchedule = scheduleXmlParser.parse(inputStream)

        assertEquals(expectedSchedule.days.size, actualSchedule.days.size)
    }
}
