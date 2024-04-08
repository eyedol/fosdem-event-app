// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.api.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class LicenseDto(
  val groupId: String,
  val artifactId: String,
  val version: String,
  val spdxLicenses: List<SpdxLicenseDto>?,
  val name: String?,
  val scm: ScmDto?,
) {
  @Serializable
  data class SpdxLicenseDto(
    val identifier: String,
    val name: String,
    val url: String,
  )

  @Serializable
  data class ScmDto(
    val url: String,
  )
}
