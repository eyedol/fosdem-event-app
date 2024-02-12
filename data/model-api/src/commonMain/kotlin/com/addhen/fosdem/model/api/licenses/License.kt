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
