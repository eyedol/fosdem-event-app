// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.model.api.licenses

data class License(
  val groupId: String,
  val artifactId: String,
  val version: String,
  val spdxLicenses: List<SpdxLicense>?,
  val name: String?,
  val scm: Scm?,
) {
  data class SpdxLicense(
    val identifier: String,
    val name: String,
    val url: String,
  )

  data class Scm(
    val url: String,
  )
}
