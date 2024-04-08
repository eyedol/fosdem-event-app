// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.configureKotlin
import com.addhen.fosdem.gradle.configureSpotless
import org.gradle.api.Plugin
import org.gradle.api.Project

class KotlinAndroidConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.android")
      }

      configureSpotless()
      configureKotlin()
    }
  }
}
