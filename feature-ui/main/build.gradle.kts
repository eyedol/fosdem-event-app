// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose.android")
  id("com.addhen.fosdem.compose")
  id("com.addhen.fosdem.kmp.serialization")
  alias(libs.plugins.kotlin.parcelize)
}

android {
  namespace = "com.addhen.fosdem.ui.main"
}

kotlin {
  sourceSets {

    androidMain {
      dependencies {
        implementation(libs.androidx.activity.compose)
      }
    }

    jvmMain {
      dependencies {
        implementation(libs.ktor.client.okhttp)
      }
    }

    commonMain {
      dependencies {
        implementation(projects.data.eventsApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(projects.coreApi)
        implementation(projects.composeUi.commonApi)
        implementation(libs.circuit.runtime)
        implementation(libs.circuit.foundation)
        implementation(libs.circuit.overlay)
        implementation(libs.circuitx.gesture.navigation)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(compose.components.resources)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
      }
    }
  }
}
