// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
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
    commonMain {
      dependencies {
        implementation(projects.coreApi)
        implementation(projects.composeUi.html)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        implementation(compose.runtime)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        implementation(compose.components.resources)
        implementation(libs.circuit.foundation)
        implementation(libs.benasher.uuid)
        api(compose.material3)
        api(libs.compose.material3.windowsizeclass)
        api(libs.lyricist.library)
      }
    }

    androidMain {
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
