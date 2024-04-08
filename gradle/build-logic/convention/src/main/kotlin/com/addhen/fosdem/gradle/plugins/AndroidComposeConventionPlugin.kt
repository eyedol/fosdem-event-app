// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.androidLibrary
import com.addhen.fosdem.gradle.debugImplementation
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
        dependencies {
          implementation(libs.findLibrary("compose.ui.tooling.preview"))
          debugImplementation(libs.findLibrary("compose.ui.tooling"))
          lintChecks(libs.findLibrary("compose.lint.check"))
        }
      }
    }
  }
}
