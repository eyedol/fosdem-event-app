// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api.entities

data class LinkEntity(
  val id: Long,
  val url: String,
  val text: String,
)
