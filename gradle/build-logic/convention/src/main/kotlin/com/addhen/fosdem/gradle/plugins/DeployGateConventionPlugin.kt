// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.configureDeployGate
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class DeployGateConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) = with(target) {
    configureDeployGate()
  }
}
