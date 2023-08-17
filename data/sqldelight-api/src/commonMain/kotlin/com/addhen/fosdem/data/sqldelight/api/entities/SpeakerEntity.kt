// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

data class SpeakerEntity(
  val id: Long,
  val name: String,
  val event_id: Long = 0,
)
