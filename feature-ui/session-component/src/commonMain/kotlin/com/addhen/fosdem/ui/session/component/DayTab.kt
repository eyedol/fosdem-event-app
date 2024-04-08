// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.addhen.fosdem.core.api.currentYear
import com.addhen.fosdem.core.api.timeZoneBrussels
import com.addhen.fosdem.model.api.Day
import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

@Immutable
data class DayTab(val id: Long, val date: LocalDate) {
  val title: String
    get() = date.dayOfWeek.toString().lowercase()
      .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

  companion object {

    val Saver: Saver<DayTab, Any> = listSaver(
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
        if (Clock.System.now()
            .toLocalDateTime(timeZoneBrussels).dayOfWeek <= entry.date.dayOfWeek
        ) {
          selectedDay = entry
        }
      }
      return selectedDay
    }
  }
}

val saturday = firstSaturdayOfFebruary()
val saturdayTab = DayTab(1, saturday)
val sundayTab = DayTab(2, saturday.plus(1, DateTimeUnit.DAY))
val dayTabs = listOf(saturdayTab, sundayTab).toPersistentList()

fun Day.toDayTab() = DayTab(id, date)

fun firstSaturdayOfFebruary(): LocalDate {
  var date = LocalDate(currentYear, 2, 1)
  while (date.dayOfWeek != DayOfWeek.SATURDAY) {
    date = date.plus(1, DateTimeUnit.DAY)
  }
  return date
}
