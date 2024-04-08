// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events

import java.io.IOException

object TestResourceUtil {

  @Throws(IOException::class)
  fun readResource(resourceName: String): String {
    return TestResourceUtil::class.java.getResource("/$resourceName")?.readText() ?: ""
  }

  fun readScheduleXml(): String {
    return readResource("schedules.xml")
  }
}
