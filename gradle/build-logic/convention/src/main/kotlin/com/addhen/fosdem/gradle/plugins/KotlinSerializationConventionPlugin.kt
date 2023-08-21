// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KotlinSerializationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.serialization")
      }
    }
  }
}
