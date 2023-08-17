// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

import kotlinx.datetime.LocalDate

data class DayEntity(
  val id: Long,
  val date: LocalDate,
)
