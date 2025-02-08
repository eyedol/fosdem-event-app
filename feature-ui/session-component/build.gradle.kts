// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose.android")
  id("com.addhen.fosdem.compose")
}

android {
  namespace = "com.addhen.fosdem.ui.session.common"
}

kotlin {
  sourceSets {

    jvmMain {
      dependencies {
        implementation(libs.compose.ui.tooling.preview)
      }
    }

    commonMain {
      dependencies {
        implementation(projects.data.modelApi)
        implementation(projects.coreApi)
        implementation(projects.composeUi.commonApi)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        implementation(compose.components.resources)
      }
    }
  }
}
