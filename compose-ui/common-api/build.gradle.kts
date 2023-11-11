// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose.android")
  id("com.addhen.fosdem.compose")
  alias(libs.plugins.kotlin.parcelize)
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.coreApi)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        implementation(libs.circuit.foundation)
        implementation(libs.benasher.uuid)
        api(compose.material3)
        api(libs.compose.material3.windowsizeclass)
        api(libs.lyricist.library)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.compose.ui.tooling.preview)
        implementation(libs.androidx.activity.compose)
      }
    }
  }
}

android {
  namespace = "com.addhen.fosdem.compose.common.ui.api"
  sourceSets {
    named("main") {
      res.srcDirs("src/commonMain/resources", "src/androidMain/resources", "src/androidMain/res")
      resources.srcDirs("src/commonMain/resources")
    }
  }
}
