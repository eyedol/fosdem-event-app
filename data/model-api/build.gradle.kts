// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
}

android.namespace = "com.addhen.fosdem.model.api"

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.coreApi)
        implementation(libs.kotlinx.datetime)
      }
    }
  }
}
