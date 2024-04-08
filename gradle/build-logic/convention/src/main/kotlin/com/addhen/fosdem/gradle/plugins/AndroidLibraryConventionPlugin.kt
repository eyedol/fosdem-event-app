// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.androidLibrary
import com.addhen.fosdem.gradle.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibraryConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    with(pluginManager) {
      apply("com.android.library")
      apply("org.gradle.android.cache-fix")
    }

    androidLibrary {
      configureAndroid()
    }
  }
}
