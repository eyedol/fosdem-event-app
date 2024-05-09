// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.kmp.serialization")
  alias(libs.plugins.kotlin.parcelize)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.serialization)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.xml.util.serialization)
        implementation(libs.kotlinx.datetime)
        api(libs.kotlininject.runtime)
        api(libs.circuit.runtime)
        api(libs.lyricist.core)
        api(libs.touchlab.kermit)
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

  targets.configureEach {
    val isAndroidTarget = platformType == KotlinPlatformType.androidJvm
    compilations.configureEach {
      compileTaskProvider.configure {
        compilerOptions {
          if (isAndroidTarget) {
            freeCompilerArgs.addAll(
              "-P",
              "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.addhen.fosdem.core.api.screens.Parcelize",
            )
          }
        }
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

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // Enable experimental 'expect'/'actual' classes
    // https://youtrack.jetbrains.com/issue/KT-61573
    allWarningsAsErrors = false
  }
}
