// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.kmp.serialization")
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(projects.coreApi)
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.kotlinx.datetime)
        implementation(libs.xml.util.serialization)
        implementation(libs.touchlab.kermit)
        api(libs.kotlininject.runtime)
        api(libs.circuit.runtime)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.xml.util.serialization.android)
        implementation(libs.sqldelight.android)
        implementation(libs.ktor.client.okhttp)
      }
    }

    jvmMain {
      dependencies {
        implementation(libs.xml.util.serialization.jvm)
        implementation(libs.ktor.client.okhttp)
        implementation(libs.sqldelight.sqlite)
      }
    }

    iosMain {
      dependencies {
        implementation(libs.sqldelight.native)
        implementation(libs.ktor.client.darwin)
      }
    }
  }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // Have to disable this due to 'duplicate library name'
    // https://youtrack.jetbrains.com/issue/KT-51110
    allWarningsAsErrors = false
  }
}

android {
  namespace = "com.addhen.fosdem.data.core.api"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
