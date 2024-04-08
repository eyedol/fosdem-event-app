// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val timeZoneBrussels = TimeZone.of("Europe/Brussels")
val currentYear = Clock.System.now().toLocalDateTime(timeZoneBrussels).year

val baseUrl = "https://fosdem.org/$currentYear/schedule/xml"
