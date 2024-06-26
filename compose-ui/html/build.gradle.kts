// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose")
  alias(libs.plugins.kotlin.parcelize)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.touchlab.kermit)
        implementation(libs.ksoup.html)
        implementation(compose.foundation)
      }
    }
  }
}

android {
  namespace = "com.addhen.fosdem.compose.ui.html"
}
