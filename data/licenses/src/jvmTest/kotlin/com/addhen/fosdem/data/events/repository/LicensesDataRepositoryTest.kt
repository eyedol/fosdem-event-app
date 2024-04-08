// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import com.addhen.fosdem.data.licenses.api.api.LicensesApi
import com.addhen.fosdem.data.licenses.api.api.dto.LicenseDto
import com.addhen.fosdem.data.licenses.repository.LicensesDataRepository
import com.addhen.fosdem.data.licenses.repository.mapper.toLicense
import com.addhen.fosdem.test.CoroutineTestRule
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

class LicensesDataRepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private lateinit var licensesDataRepository: LicensesDataRepository

  @Test
  fun `retrieves licenses successfully`() = coroutineTestRule.runTest {
    val fake = HappyPathLicensesApi(coroutineTestRule.testDispatcherProvider)
    val expectedLicenses = fake.getLicenses().map { it.toLicense() }
    licensesDataRepository = LicensesDataRepository(fake)
    val actual = licensesDataRepository.getLicenses()

    assertEquals(expectedLicenses, actual)
  }

  @Test
  fun `retrieves licenses with error`() = coroutineTestRule.runTest {
    licensesDataRepository = LicensesDataRepository(
      UnhappyPathLicensesApi(coroutineTestRule.testDispatcherProvider),
    )

    try {
      licensesDataRepository.getLicenses()
    } catch (e: Exception) {
      assertEquals(RuntimeException::class, e::class)
    }
  }

  internal class HappyPathLicensesApi(
    private val dispatchers: AppCoroutineDispatchers,
  ) : LicensesApi {

    private val licenseJSON = """
    [
    {
        "groupId": "androidx.activity",
        "artifactId": "activity",
        "version": "1.8.2",
        "name": "Activity",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.activity",
        "artifactId": "activity-compose",
        "version": "1.8.2",
        "name": "Activity Compose",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.activity",
        "artifactId": "activity-ktx",
        "version": "1.8.2",
        "name": "Activity Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.annotation",
        "artifactId": "annotation",
        "version": "1.7.0",
        "name": "Annotation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.annotation",
        "artifactId": "annotation-experimental",
        "version": "1.3.0",
        "name": "Experimental annotation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.annotation",
        "artifactId": "annotation-jvm",
        "version": "1.7.0",
        "name": "Annotation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.arch.core",
        "artifactId": "core-common",
        "version": "2.2.0",
        "name": "Android Arch-Common",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.arch.core",
        "artifactId": "core-runtime",
        "version": "2.2.0",
        "name": "Android Arch-Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.autofill",
        "artifactId": "autofill",
        "version": "1.0.0",
        "name": "AndroidX Autofill",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "http://source.android.com"
        }
    },
    {
        "groupId": "androidx.browser",
        "artifactId": "browser",
        "version": "1.7.0",
        "name": "Browser",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.collection",
        "artifactId": "collection",
        "version": "1.4.0",
        "name": "collections",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.collection",
        "artifactId": "collection-jvm",
        "version": "1.4.0",
        "name": "collections",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.collection",
        "artifactId": "collection-ktx",
        "version": "1.4.0",
        "name": "Collections Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation",
        "version": "1.6.1",
        "name": "Compose Animation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation-android",
        "version": "1.6.1",
        "name": "Compose Animation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation-core",
        "version": "1.6.1",
        "name": "Compose Animation Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation-core-android",
        "version": "1.6.1",
        "name": "Compose Animation Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation-graphics",
        "version": "1.6.1",
        "name": "Compose Animation Graphics",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.animation",
        "artifactId": "animation-graphics-android",
        "version": "1.6.1",
        "name": "Compose Animation Graphics",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.foundation",
        "artifactId": "foundation",
        "version": "1.6.1",
        "name": "Compose Foundation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.foundation",
        "artifactId": "foundation-android",
        "version": "1.6.1",
        "name": "Compose Foundation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.foundation",
        "artifactId": "foundation-layout",
        "version": "1.6.1",
        "name": "Compose Layouts",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.foundation",
        "artifactId": "foundation-layout-android",
        "version": "1.6.1",
        "name": "Compose Layouts",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material",
        "version": "1.5.4",
        "name": "Compose Material Components",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-android",
        "version": "1.5.4",
        "name": "Compose Material Components",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-icons-core",
        "version": "1.5.4",
        "name": "Compose Material Icons Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-icons-core-android",
        "version": "1.5.4",
        "name": "Compose Material Icons Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-icons-extended",
        "version": "1.5.4",
        "name": "Compose Material Icons Extended",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-icons-extended-android",
        "version": "1.5.4",
        "name": "Compose Material Icons Extended",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-ripple",
        "version": "1.5.4",
        "name": "Compose Material Ripple",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material",
        "artifactId": "material-ripple-android",
        "version": "1.5.4",
        "name": "Compose Material Ripple",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.material3",
        "artifactId": "material3",
        "version": "1.1.2",
        "name": "Compose Material3 Components",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.runtime",
        "artifactId": "runtime",
        "version": "1.6.1",
        "name": "Compose Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.runtime",
        "artifactId": "runtime-android",
        "version": "1.6.1",
        "name": "Compose Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.runtime",
        "artifactId": "runtime-saveable",
        "version": "1.6.1",
        "name": "Compose Saveable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.runtime",
        "artifactId": "runtime-saveable-android",
        "version": "1.6.1",
        "name": "Compose Saveable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui",
        "version": "1.6.1",
        "name": "Compose UI",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-android",
        "version": "1.6.1",
        "name": "Compose UI",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-geometry",
        "version": "1.6.1",
        "name": "Compose Geometry",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-geometry-android",
        "version": "1.6.1",
        "name": "Compose Geometry",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-graphics",
        "version": "1.6.1",
        "name": "Compose Graphics",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-graphics-android",
        "version": "1.6.1",
        "name": "Compose Graphics",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-text",
        "version": "1.6.1",
        "name": "Compose UI Text",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-text-android",
        "version": "1.6.1",
        "name": "Compose UI Text",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling",
        "version": "1.6.1",
        "name": "Compose Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling-android",
        "version": "1.6.1",
        "name": "Compose Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling-data",
        "version": "1.6.1",
        "name": "Compose Tooling Data",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling-data-android",
        "version": "1.6.1",
        "name": "Compose Tooling Data",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling-preview",
        "version": "1.6.1",
        "name": "Compose UI Preview Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-tooling-preview-android",
        "version": "1.6.1",
        "name": "Compose UI Preview Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-unit",
        "version": "1.6.1",
        "name": "Compose Unit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-unit-android",
        "version": "1.6.1",
        "name": "Compose Unit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-util",
        "version": "1.6.1",
        "name": "Compose Util",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.compose.ui",
        "artifactId": "ui-util-android",
        "version": "1.6.1",
        "name": "Compose Util",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.concurrent",
        "artifactId": "concurrent-futures",
        "version": "1.1.0",
        "name": "AndroidX Futures",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "http://source.android.com"
        }
    },
    {
        "groupId": "androidx.core",
        "artifactId": "core",
        "version": "1.12.0",
        "name": "Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.core",
        "artifactId": "core-ktx",
        "version": "1.12.0",
        "name": "Core Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.customview",
        "artifactId": "customview-poolingcontainer",
        "version": "1.0.0",
        "name": "androidx.customview:poolingcontainer",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.emoji2",
        "artifactId": "emoji2",
        "version": "1.3.0",
        "name": "Android Emoji2 Compat",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.interpolator",
        "artifactId": "interpolator",
        "version": "1.0.0",
        "name": "Android Support Library Interpolators",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "http://source.android.com"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-common",
        "version": "2.7.0",
        "name": "Lifecycle-Common",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-common-java8",
        "version": "2.7.0",
        "name": "Lifecycle-Common for Java 8",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-livedata-core",
        "version": "2.7.0",
        "name": "Lifecycle LiveData Core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-process",
        "version": "2.7.0",
        "name": "Lifecycle Process",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-runtime",
        "version": "2.7.0",
        "name": "Lifecycle Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-runtime-ktx",
        "version": "2.7.0",
        "name": "Lifecycle Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-viewmodel",
        "version": "2.7.0",
        "name": "Lifecycle ViewModel",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-viewmodel-compose",
        "version": "2.7.0",
        "name": "Lifecycle ViewModel Compose",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-viewmodel-ktx",
        "version": "2.7.0",
        "name": "Lifecycle ViewModel Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.lifecycle",
        "artifactId": "lifecycle-viewmodel-savedstate",
        "version": "2.7.0",
        "name": "Lifecycle ViewModel with SavedState",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.profileinstaller",
        "artifactId": "profileinstaller",
        "version": "1.3.0",
        "name": "androidx.profileinstaller:profileinstaller",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.savedstate",
        "artifactId": "savedstate",
        "version": "1.2.1",
        "name": "Saved State",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.savedstate",
        "artifactId": "savedstate-ktx",
        "version": "1.2.1",
        "name": "SavedState Kotlin Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.sqlite",
        "artifactId": "sqlite",
        "version": "2.4.0",
        "name": "SQLite",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.sqlite",
        "artifactId": "sqlite-framework",
        "version": "2.4.0",
        "name": "SQLite Framework Integration",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.startup",
        "artifactId": "startup-runtime",
        "version": "1.1.1",
        "name": "Android App Startup Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.tracing",
        "artifactId": "tracing",
        "version": "1.0.0",
        "name": "Android Tracing",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.versionedparcelable",
        "artifactId": "versionedparcelable",
        "version": "1.1.1",
        "name": "VersionedParcelable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "http://source.android.com"
        }
    },
    {
        "groupId": "androidx.window",
        "artifactId": "window",
        "version": "1.2.0",
        "name": "WindowManager",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "androidx.window.extensions.core",
        "artifactId": "core",
        "version": "1.0.0",
        "name": "Jetpack WindowManager library Core Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://cs.android.com/androidx/platform/frameworks/support"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "android-driver",
        "version": "2.0.1",
        "name": "SQLDelight Android Driver",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "async-extensions",
        "version": "2.0.1",
        "name": "SQLDelight Async Driver Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "async-extensions-jvm",
        "version": "2.0.1",
        "name": "SQLDelight Async Driver Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "coroutines-extensions",
        "version": "2.0.1",
        "name": "SQLDelight Coroutines Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "coroutines-extensions-jvm",
        "version": "2.0.1",
        "name": "SQLDelight Coroutines Extensions",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "primitive-adapters",
        "version": "2.0.1",
        "name": "SQLDelight Primitive Column Adapters",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "primitive-adapters-jvm",
        "version": "2.0.1",
        "name": "SQLDelight Primitive Column Adapters",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "runtime",
        "version": "2.0.1",
        "name": "SQLDelight Multiplatform Runtime (Experimental)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "app.cash.sqldelight",
        "artifactId": "runtime-jvm",
        "version": "2.0.1",
        "name": "SQLDelight Multiplatform Runtime (Experimental)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/cashapp/sqldelight/"
        }
    },
    {
        "groupId": "cafe.adriel.lyricist",
        "artifactId": "lyricist",
        "version": "1.6.2-1.8.20",
        "name": "Lyricist",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/adrielcafe/lyricist"
        }
    },
    {
        "groupId": "cafe.adriel.lyricist",
        "artifactId": "lyricist-android",
        "version": "1.6.2-1.8.20",
        "name": "Lyricist",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/adrielcafe/lyricist"
        }
    },
    {
        "groupId": "cafe.adriel.lyricist",
        "artifactId": "lyricist-core",
        "version": "1.6.2-1.8.20",
        "name": "Lyricist Core",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/adrielcafe/lyricist"
        }
    },
    {
        "groupId": "cafe.adriel.lyricist",
        "artifactId": "lyricist-core-android",
        "version": "1.6.2-1.8.20",
        "name": "Lyricist Core",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/adrielcafe/lyricist"
        }
    },
    {
        "groupId": "co.touchlab",
        "artifactId": "kermit",
        "version": "2.0.3",
        "name": "Kermit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/touchlab/Kermit"
        }
    },
    {
        "groupId": "co.touchlab",
        "artifactId": "kermit-android-debug",
        "version": "2.0.3",
        "name": "Kermit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/touchlab/Kermit"
        }
    },
    {
        "groupId": "co.touchlab",
        "artifactId": "kermit-core",
        "version": "2.0.3",
        "name": "Kermit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/touchlab/Kermit"
        }
    },
    {
        "groupId": "co.touchlab",
        "artifactId": "kermit-core-android-debug",
        "version": "2.0.3",
        "name": "Kermit",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/touchlab/Kermit"
        }
    },
    {
        "groupId": "com.benasher44",
        "artifactId": "uuid",
        "version": "0.8.2",
        "name": "UUID",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/benasher44/uuid/"
        }
    },
    {
        "groupId": "com.benasher44",
        "artifactId": "uuid-jvm",
        "version": "0.8.2",
        "name": "UUID",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/benasher44/uuid/"
        }
    },
    {
        "groupId": "com.google.guava",
        "artifactId": "listenablefuture",
        "version": "1.0",
        "name": "Guava ListenableFuture only",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/google/guava"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-backstack",
        "version": "0.19.1",
        "name": "Circuit (Backstack)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-backstack-android",
        "version": "0.19.1",
        "name": "Circuit (Backstack)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-foundation",
        "version": "0.19.1",
        "name": "Circuit (Foundation)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-foundation-android",
        "version": "0.19.1",
        "name": "Circuit (Foundation)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-overlay",
        "version": "0.19.1",
        "name": "Circuit (Overlay)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-overlay-android",
        "version": "0.19.1",
        "name": "Circuit (Overlay)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-retained",
        "version": "0.19.1",
        "name": "Circuit (retained)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-retained-android",
        "version": "0.19.1",
        "name": "Circuit (retained)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime",
        "version": "0.19.1",
        "name": "Circuit (runtime)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-android",
        "version": "0.19.1",
        "name": "Circuit (runtime)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-presenter",
        "version": "0.19.1",
        "name": "Circuit (Presenter)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-presenter-android",
        "version": "0.19.1",
        "name": "Circuit (Presenter)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-screen",
        "version": "0.19.1",
        "name": "Circuit (Screen)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-screen-android",
        "version": "0.19.1",
        "name": "Circuit (Screen)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-ui",
        "version": "0.19.1",
        "name": "Circuit (UI)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuit-runtime-ui-android",
        "version": "0.19.1",
        "name": "Circuit (UI)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuitx-gesture-navigation",
        "version": "0.19.1",
        "name": "CircuitX (Gesture Navigation)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.slack.circuit",
        "artifactId": "circuitx-gesture-navigation-android",
        "version": "0.19.1",
        "name": "CircuitX (Gesture Navigation)",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/slackhq/circuit/"
        }
    },
    {
        "groupId": "com.squareup.okhttp3",
        "artifactId": "okhttp",
        "version": "4.12.0",
        "name": "okhttp",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/square/okhttp"
        }
    },
    {
        "groupId": "com.squareup.okio",
        "artifactId": "okio",
        "version": "3.7.0",
        "name": "okio",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/square/okio/"
        }
    },
    {
        "groupId": "com.squareup.okio",
        "artifactId": "okio-jvm",
        "version": "3.7.0",
        "name": "okio",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/square/okio/"
        }
    },
    {
        "groupId": "dev.chrisbanes.material3",
        "artifactId": "material3-window-size-class-multiplatform",
        "version": "0.3.2",
        "name": "Compose Material 3 Window Size Class",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/chrisbanes/material3-windowsizeclass-multiplatform/"
        }
    },
    {
        "groupId": "dev.chrisbanes.material3",
        "artifactId": "material3-window-size-class-multiplatform-android",
        "version": "0.3.2",
        "name": "Compose Material 3 Window Size Class",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/chrisbanes/material3-windowsizeclass-multiplatform/"
        }
    },
    {
        "groupId": "io.github.pdvrieze.xmlutil",
        "artifactId": "core",
        "version": "0.86.3",
        "name": "core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/pdvrieze/xmlutil"
        }
    },
    {
        "groupId": "io.github.pdvrieze.xmlutil",
        "artifactId": "core-android",
        "version": "0.86.3",
        "name": "core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/pdvrieze/xmlutil"
        }
    },
    {
        "groupId": "io.github.pdvrieze.xmlutil",
        "artifactId": "serialization",
        "version": "0.86.3",
        "name": "serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/pdvrieze/xmlutil"
        }
    },
    {
        "groupId": "io.github.pdvrieze.xmlutil",
        "artifactId": "serialization-android",
        "version": "0.86.3",
        "name": "serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/pdvrieze/xmlutil"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-content-negotiation",
        "version": "2.3.8",
        "name": "ktor-client-content-negotiation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-content-negotiation-jvm",
        "version": "2.3.8",
        "name": "ktor-client-content-negotiation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-core",
        "version": "2.3.8",
        "name": "ktor-client-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-core-jvm",
        "version": "2.3.8",
        "name": "ktor-client-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-json",
        "version": "2.3.8",
        "name": "ktor-client-json",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-json-jvm",
        "version": "2.3.8",
        "name": "ktor-client-json",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-logging",
        "version": "2.3.8",
        "name": "ktor-client-logging",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-logging-jvm",
        "version": "2.3.8",
        "name": "ktor-client-logging",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-okhttp",
        "version": "2.3.8",
        "name": "ktor-client-okhttp",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-okhttp-jvm",
        "version": "2.3.8",
        "name": "ktor-client-okhttp",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-serialization",
        "version": "2.3.8",
        "name": "ktor-client-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-client-serialization-jvm",
        "version": "2.3.8",
        "name": "ktor-client-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-events",
        "version": "2.3.8",
        "name": "ktor-events",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-events-jvm",
        "version": "2.3.8",
        "name": "ktor-events",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-http",
        "version": "2.3.8",
        "name": "ktor-http",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-http-jvm",
        "version": "2.3.8",
        "name": "ktor-http",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-io",
        "version": "2.3.8",
        "name": "ktor-io",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-io-jvm",
        "version": "2.3.8",
        "name": "ktor-io",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-serialization",
        "version": "2.3.8",
        "name": "ktor-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-serialization-jvm",
        "version": "2.3.8",
        "name": "ktor-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-utils",
        "version": "2.3.8",
        "name": "ktor-utils",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-utils-jvm",
        "version": "2.3.8",
        "name": "ktor-utils",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-websocket-serialization",
        "version": "2.3.8",
        "name": "ktor-websocket-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-websocket-serialization-jvm",
        "version": "2.3.8",
        "name": "ktor-websocket-serialization",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-websockets",
        "version": "2.3.8",
        "name": "ktor-websockets",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "io.ktor",
        "artifactId": "ktor-websockets-jvm",
        "version": "2.3.8",
        "name": "ktor-websockets",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/ktorio/ktor.git"
        }
    },
    {
        "groupId": "me.tatarka.inject",
        "artifactId": "kotlin-inject-runtime",
        "version": "0.6.3",
        "name": "kotlin-inject",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/evant/kotlin-inject"
        }
    },
    {
        "groupId": "me.tatarka.inject",
        "artifactId": "kotlin-inject-runtime-jvm",
        "version": "0.6.3",
        "name": "kotlin-inject",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/evant/kotlin-inject"
        }
    },
    {
        "groupId": "org.jetbrains",
        "artifactId": "annotations",
        "version": "23.0.0",
        "name": "JetBrains Java Annotations",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/java-annotations"
        }
    },
    {
        "groupId": "org.jetbrains.compose.animation",
        "artifactId": "animation-graphics",
        "version": "1.5.12",
        "name": "Compose Animation Graphics",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.components",
        "artifactId": "components-resources",
        "version": "1.5.12",
        "name": "Resources for Compose JB",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.components",
        "artifactId": "library-android",
        "version": "1.5.12",
        "name": "Resources for Compose JB",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.foundation",
        "artifactId": "foundation",
        "version": "1.5.12",
        "name": "Compose Foundation",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.material",
        "artifactId": "material",
        "version": "1.5.12",
        "name": "Compose Material Components",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.material",
        "artifactId": "material-icons-extended",
        "version": "1.5.12",
        "name": "Compose Material Icons Extended",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.material3",
        "artifactId": "material3",
        "version": "1.5.12",
        "name": "Compose Material3 Components",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.runtime",
        "artifactId": "runtime",
        "version": "1.5.12",
        "name": "Compose Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.runtime",
        "artifactId": "runtime-saveable",
        "version": "1.5.12",
        "name": "Compose Saveable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.ui",
        "artifactId": "ui",
        "version": "1.5.12",
        "name": "Compose UI primitives",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.ui",
        "artifactId": "ui-tooling",
        "version": "1.5.12",
        "name": "Compose Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.ui",
        "artifactId": "ui-tooling-preview",
        "version": "1.5.12",
        "name": "Compose UI Preview Tooling",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.compose.ui",
        "artifactId": "ui-util",
        "version": "1.5.12",
        "name": "Compose Util",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/compose-jb"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-android-extensions-runtime",
        "version": "1.9.22",
        "name": "Kotlin Android Extensions Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-bom",
        "version": "1.9.22",
        "name": "Kotlin Libraries bill-of-materials",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-parcelize-runtime",
        "version": "1.9.22",
        "name": "Parcelize Runtime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-stdlib",
        "version": "1.9.22",
        "name": "Kotlin Stdlib",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-stdlib-common",
        "version": "1.9.22",
        "name": "Kotlin Stdlib Common",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-stdlib-jdk7",
        "version": "1.9.22",
        "name": "Kotlin Stdlib Jdk7",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlin",
        "artifactId": "kotlin-stdlib-jdk8",
        "version": "1.9.22",
        "name": "Kotlin Stdlib Jdk8",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/JetBrains/kotlin"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "atomicfu",
        "version": "0.23.2",
        "name": "atomicfu",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.atomicfu"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "atomicfu-jvm",
        "version": "0.23.2",
        "name": "atomicfu",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.atomicfu"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-collections-immutable",
        "version": "0.3.7",
        "name": "kotlinx-collections-immutable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.collections.immutable"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-collections-immutable-jvm",
        "version": "0.3.7",
        "name": "kotlinx-collections-immutable",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.collections.immutable"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-android",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-android",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-bom",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-bom",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-core",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-core-jvm",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-jdk8",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-jdk8",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-coroutines-slf4j",
        "version": "1.7.3",
        "name": "kotlinx-coroutines-slf4j",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.coroutines"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-datetime",
        "version": "0.5.0",
        "name": "kotlinx-datetime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx-datetime"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-datetime-jvm",
        "version": "0.5.0",
        "name": "kotlinx-datetime",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx-datetime"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-serialization-bom",
        "version": "1.6.3",
        "name": "kotlinx-serialization-bom",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.serialization"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-serialization-core",
        "version": "1.6.3",
        "name": "kotlinx-serialization-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.serialization"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-serialization-core-jvm",
        "version": "1.6.3",
        "name": "kotlinx-serialization-core",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.serialization"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-serialization-json",
        "version": "1.6.3",
        "name": "kotlinx-serialization-json",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.serialization"
        }
    },
    {
        "groupId": "org.jetbrains.kotlinx",
        "artifactId": "kotlinx-serialization-json-jvm",
        "version": "1.6.3",
        "name": "kotlinx-serialization-json",
        "spdxLicenses": [
            {
                "identifier": "Apache-2.0",
                "name": "Apache License 2.0",
                "url": "https://www.apache.org/licenses/LICENSE-2.0"
            }
        ],
        "scm": {
            "url": "https://github.com/Kotlin/kotlinx.serialization"
        }
    },
    {
        "groupId": "org.slf4j",
        "artifactId": "slf4j-api",
        "version": "1.7.36",
        "name": "SLF4J API Module",
        "spdxLicenses": [
            {
                "identifier": "MIT",
                "name": "MIT License",
                "url": "https://opensource.org/licenses/MIT"
            }
        ],
        "scm": {
            "url": "https://github.com/qos-ch/slf4j"
        }
    }
]
    """.trimIndent()

    override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
      getLicenses()
    }

    fun getLicenses(): List<LicenseDto> {
      return Json.decodeFromString<List<LicenseDto>>(licenseJSON)
    }
  }

  internal class UnhappyPathLicensesApi(
    private val dispatchers: AppCoroutineDispatchers,
  ) : LicensesApi {
    override suspend fun fetchLicenses(): List<LicenseDto> = withContext(dispatchers.io) {
      throw RuntimeException("Unhappy path")
    }
  }
}
