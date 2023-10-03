// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

data class SearchQuery(val queryText: String) {
  val hasQuery get() = queryText.isNotBlank()

  fun String.getMatchIndexRange(): IntRange {
    if (!hasQuery) return IntRange.EMPTY

    val startIndex = this.indexOf(queryText, ignoreCase = true)
    return if (startIndex >= 0) {
      startIndex until (startIndex + queryText.length)
    } else {
      IntRange.EMPTY
    }
  }

  companion object {
    val Empty = SearchQuery("AAA")
  }
}
