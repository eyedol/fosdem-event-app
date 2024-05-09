// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.kmp.serialization")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.licenses.api"

kotlin {
  sourceSets {
    all {
      languageSettings.apply {
        optIn("kotlin.RequiresOptIn")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.serialization)
      }
    }
  }
}
