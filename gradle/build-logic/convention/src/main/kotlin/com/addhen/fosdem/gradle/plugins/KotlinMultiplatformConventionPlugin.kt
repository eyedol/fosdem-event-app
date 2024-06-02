// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.configureIosLicensesTasks
import com.addhen.fosdem.gradle.configureKotlin
import com.addhen.fosdem.gradle.configureLicensee
import com.addhen.fosdem.gradle.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

class KotlinMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) =
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.multiplatform")
      }

      extensions.configure<KotlinMultiplatformExtension> {
        applyDefaultHierarchyTemplate()

        jvm()
        if (pluginManager.hasPlugin("com.android.library")) {
          androidTarget()
        }

        listOf(
          iosX64(),
          iosArm64(),
          iosSimulatorArm64(),
        )

        targets.withType<KotlinNativeTarget>().configureEach {
          binaries.all {
            // Add linker flag for SQLite. See:
            // https://github.com/touchlab/SQLiter/issues/77
            linkerOpts("-lsqlite3")
          }

          compilations.configureEach {
            compileTaskProvider.configure {
              compilerOptions {
                // Various opt-ins
                freeCompilerArgs.addAll(
                  "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
                  "-opt-in=kotlinx.cinterop.BetaInteropApi",
                  "-Xexpect-actual-classes",
                )
              }
            }
          }
        }

        configureSpotless()
        configureKotlin()

        if (path == ":ios-shared") {
          configureLicensee()
          configureIosLicensesTasks()
        }
      }
    }
}

fun Project.addKspDependencyForAllTargets(dependencyNotation: Any) =
  addKspDependencyForAllTargets("", dependencyNotation)

private fun Project.addKspDependencyForAllTargets(
  configurationNameSuffix: String,
  dependencyNotation: Any,
) {
  val kmpExtension = extensions.getByType<KotlinMultiplatformExtension>()
  dependencies {
    kmpExtension.targets
      .asSequence()
      .filter { target ->
        // Don't add KSP for common target, only final platforms
        target.platformType != KotlinPlatformType.common
      }
      .forEach { target ->
        add(
          "ksp${target.targetName.capitalized()}$configurationNameSuffix",
          dependencyNotation,
        )
      }
  }
}
