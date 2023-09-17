// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.android
import com.addhen.fosdem.gradle.androidLibrary
import com.addhen.fosdem.gradle.implementation
import com.addhen.fosdem.gradle.libs
import com.addhen.fosdem.gradle.lintChecks
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidComposeConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      androidLibrary {
        android {
          buildFeatures.compose = true
          composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompiler").get().toString()
          }
        }
        dependencies {
          implementation(libs.findLibrary("compose.ui.tooling.preview"))
          lintChecks(libs.findLibrary("compose.lint.check"))

          // add("coreLibraryDesugaring", libs.findLibrary("android.desugaring").get())
        }
      }
    }
  }
}
