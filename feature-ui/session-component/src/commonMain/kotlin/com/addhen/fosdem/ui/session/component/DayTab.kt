// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.addhen.fosdem.model.api.Day
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Immutable
data class DayTab(val id: Long, val date: LocalDate) {
  val title: String
    get() = date.dayOfWeek.toString().lowercase()
      .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
  val year: String
    get() = (date.year % 100).toString()
  companion object {
    private val tzBrussels = TimeZone.of("Europe/Brussels")

    val Saver: Saver<DayTab, *> = listSaver(
      save = { listOf(it.id.toString(), it.date.toString()) },
      restore = {
        DayTab(
          id = it.first().toLong(),
          date = LocalDate.parse(it.last().toString()),
        )
      },
    )

    fun selectDay(days: List<DayTab>): DayTab {
      val reversedEntries = days.sortedByDescending { it.id }
      var selectedDay = reversedEntries.last()
      for (entry in reversedEntries) {
        if (Clock.System.now().toLocalDateTime(tzBrussels).dayOfWeek <= entry.date.dayOfWeek) {
          selectedDay = entry
        }
      }
      return selectedDay
    }
  }
}

val dayTab1 = DayTab(
  1,
  LocalDate.parse("2023-02-04"),
)

val dayTab2 = DayTab(
  2,
  LocalDate.parse("2023-02-05"),
)

fun Day.toDayTab() = DayTab(id, date)
