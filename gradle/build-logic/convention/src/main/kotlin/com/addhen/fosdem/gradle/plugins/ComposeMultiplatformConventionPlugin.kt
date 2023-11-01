// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension

class ComposeMultiplatformConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    pluginManager.apply("org.jetbrains.compose")
    configureCompose()
  }
}

fun Project.configureCompose() {
  val composeVersion = libs.findVersion("compose-multiplatform").get().requiredVersion
  configurations.configureEach {
    resolutionStrategy.eachDependency {
      val group = requested.group

      when {
        group.startsWith("org.jetbrains.compose") && !group.endsWith("compiler") -> {
          useVersion(composeVersion)
        }
      }
    }
  }
}
