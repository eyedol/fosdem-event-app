// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api

data class Attachment(
  val id: Long,
  val eventId: Long,
  val type: String,
  val url: String,
)