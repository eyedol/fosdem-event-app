// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import com.addhen.fosdem.gradle.configureJvmLicensesTasks
import com.addhen.fosdem.gradle.configureLicensee
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
}

android.namespace = "com.addhen.fosdem.data.licenses"

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
        implementation(projects.data.licensesApi)
        implementation(projects.data.modelApi)
        implementation(libs.kotlinx.serialization)
      }
    }

    commonTest {}

    // Because Java has great tools for testing. The idea is to test common code on
    // the JVM and if something requires platform specific, test is on the specific platform.
    jvmTest {
      dependencies {
        implementation(projects.testing)
      }
    }
  }

  configureLicensee()
  configureJvmLicensesTasks()
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // You can use -Xexpect-actual-classes flag to suppress this warning.
    // https://youtrack.jetbrains.com/issue/KT-61573
    allWarningsAsErrors = false
  }
}
