// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.kmp.serialization")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.events.api"

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
    val commonMain by getting {
      dependencies {
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(projects.data.sqldelightApi)
        implementation(libs.kotlinx.datetime)
        implementation(libs.xml.util.serialization)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
      }
    }
  }
}
