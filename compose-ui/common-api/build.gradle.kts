// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose")
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.coreApi)
        implementation(libs.circuit.foundation)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        api(compose.material3)
        api(libs.compose.material3.windowsizeclass)
        api(libs.lyricist.library)
      }
    }
  }
}

android {
  namespace = "com.addhen.fosdem.compose.common.ui.api"
}
