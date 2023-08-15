// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class SpotlessConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    configureSpotless()

    afterEvaluate {
      if (tasks.findByName("check")?.enabled == true) {
        tasks.getByName("check").dependsOn("spotlessCheck")
      }
    }
  }
}
