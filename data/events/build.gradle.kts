// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.events"

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
        implementation(projects.coreApi)
        implementation(projects.data.coreApi)
        implementation(projects.data.eventsApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.sqldelightApi)
        implementation(libs.ktor.client.core)
        implementation(libs.kotlinx.datetime)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
        implementation(libs.kotlinx.coroutines.core)
      }
    }

    commonTest {}

    // Because Java has great tools for testing. The idea is to test common code on
    // the JVM and if something requires platform specific, test is on the specific platform.
    jvmTest {
      dependencies {
        implementation(projects.testing)
        implementation(libs.xml.util.serialization.jvm)
        implementation(libs.kotlinx.serialization.core)
        implementation(libs.xml.util.serialization)
      }
    }
  }
}

// Needed for tests to run otherwise it errors
tasks.withType<Test> {
  useJUnitPlatform()
}
