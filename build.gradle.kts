// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0


import org.jetbrains.kotlin.gradle.dsl.KotlinJvmCompilerOptions
import org.jetbrains.kotlin.gradle.internal.KaptGenerateStubsTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

plugins {
  id("com.addhen.fosdem.gradle.plugins.root")
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.lint) apply false
  alias(libs.plugins.cacheFixPlugin) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.android) apply false
  alias(libs.plugins.kotlin.parcelize) apply false
  alias(libs.plugins.spotless) apply false
  alias(libs.plugins.composeMultiplatform) apply false
}

subprojects {
  tasks.withType<KotlinCompilationTask<*>>().configureEach {
    compilerOptions {
      allWarningsAsErrors.set(true)
      if (this is KotlinJvmCompilerOptions) {
        // Stub gen copies args from the parent compilation
        if (this@configureEach !is KaptGenerateStubsTask) {
          freeCompilerArgs.addAll(
            "-Xjsr305=strict",
            // Match JVM assertion behavior:
            // https://publicobject.com/2019/11/18/kotlins-assert-is-not-like-javas-assert/
            "-Xassertions=jvm",
            // Potentially useful for static analysis tools or annotation processors.
            "-Xemit-jvm-type-annotations",
            // Enable new jvm-default behavior
            // https://blog.jetbrains.com/kotlin/2020/07/kotlin-1-4-m3-generating-default-methods-in-interfaces/
            "-Xjvm-default=all",
            // https://kotlinlang.org/docs/whatsnew1520.html#support-for-jspecify-nullness-annotations
            "-Xtype-enhancement-improvements-strict-mode",
            "-Xjspecify-annotations=strict",
            // expect/actual classes (including interfaces, objects, annotations, enums, actual typealiases) in Beta
            // https://youtrack.jetbrains.com/issue/KT-61573
            "-Xexpect-actual-classes",
          )
        }
      }
      progressiveMode.set(true)
    }
  }
}
