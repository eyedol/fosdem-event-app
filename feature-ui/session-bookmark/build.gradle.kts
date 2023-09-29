// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
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
  namespace = "com.addhen.fosdem.ui.session.bookmark"
}

kotlin {
  sourceSets {

    val jvmMain by getting {
      dependencies {
        implementation(libs.compose.ui.tooling.preview)
      }
    }

    val commonMain by getting {
      dependencies {
        implementation(projects.featureUi.sessionComponent)
        implementation(projects.data.eventsApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(projects.coreApi)
        implementation(projects.composeUi.commonApi)
        implementation(libs.circuit.runtime)
        implementation(libs.circuit.foundation)
        implementation(libs.circuit.overlay)
        implementation(libs.kotlinx.collections.immutable)
        implementation(libs.kotlinx.datetime)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        implementation(compose.components.resources)
      }
    }
  }
}
