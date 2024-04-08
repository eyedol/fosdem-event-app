// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.licenses.repository.mapper

import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import com.addhen.fosdem.model.api.licenses.License

fun LicenseDto.toLicense(): License {
  return License(
    groupId = groupId,
    artifactId = artifactId,
    version = version,
    name = name,
    spdxLicenses = spdxLicenses?.map { it.toSpdxLicense() },
    scm = scm.toScm(),
  )
}

private fun LicenseDto.SpdxLicenseDto.toSpdxLicense(): License.SpdxLicense {
  return License.SpdxLicense(
    identifier = identifier,
    name = name,
    url = url,
  )
}

private fun LicenseDto.ScmDto?.toScm(): License.Scm {
  return License.Scm(
    url = this?.url ?: "",
  )
}
