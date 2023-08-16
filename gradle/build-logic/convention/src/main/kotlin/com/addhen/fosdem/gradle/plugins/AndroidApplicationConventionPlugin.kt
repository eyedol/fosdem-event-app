// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0
package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.androidApplication
import com.addhen.fosdem.gradle.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("com.android.application")
      apply("org.gradle.android.cache-fix")
    }
    androidApplication {
      configureAndroid()
    }
  }
}
