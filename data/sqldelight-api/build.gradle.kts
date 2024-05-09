// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

kotlin {
  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.kotlininject.runtime)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.sqldelight.android)
      }
    }

    jvmMain {
      dependencies {
        implementation(libs.sqldelight.sqlite)
      }
    }

    iosMain {
      dependencies {
        implementation(libs.sqldelight.native)
      }
    }
  }
}

sqldelight {
  databases {
    create("Database") {
      packageName.set("com.addhen.fosdem.data.sqldelight")
    }
  }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // Have to disable this as some of the generated code has
    // warnings for unused parameters
    allWarningsAsErrors.set(false)
  }
}

android {
  namespace = "com.addhen.fosdem.data.sqldelight.api"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
