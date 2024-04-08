// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.sqldelight.database"

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.coreApi)
        implementation(projects.data.sqldelightApi)
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlininject.runtime)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.sqldelight.android)
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(libs.sqldelight.sqlite)
      }
    }

    val iosMain by getting {
      dependencies {
        implementation(libs.sqldelight.native)
      }
    }
  }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // You can use -Xexpect-actual-classes flag to suppress this warning.
    // https://youtrack.jetbrains.com/issue/KT-61573
    allWarningsAsErrors = false
  }
}
