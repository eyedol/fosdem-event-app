// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

data class Link(
  val id: Long,
  val url: String,
  val text: String,
  val eventId: Long,
)
