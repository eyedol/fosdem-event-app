// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0


import com.android.build.gradle.internal.ide.kmp.KotlinAndroidSourceSetMarker.Companion.android

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.kmp.serialization")
  alias(libs.plugins.kotlin.parcelize)
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.xml.util.serialization)
        implementation(libs.touchlab.kermit)
        api(libs.kotlininject.runtime)
        api(libs.circuit.runtime)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.xml.util.serialization.android)
        implementation(libs.sqldelight.android)
        implementation(libs.ktor.client.okhttp)
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(libs.xml.util.serialization.jvm)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.sqldelight.sqlite)
      }
    }

    val iosMain by getting {
      dependencies {
        implementation(libs.sqldelight.native)
        implementation(libs.ktor.client.darwin)
      }
    }
  }
}

android {
  namespace = "com.addhen.fosdem.core.api"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
