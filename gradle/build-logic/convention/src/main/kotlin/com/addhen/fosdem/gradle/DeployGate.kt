// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import com.deploygate.gradle.plugins.dsl.DeployGateExtension
import com.deploygate.gradle.plugins.dsl.NamedDeployment
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.kotlin.dsl.closureOf
import org.gradle.kotlin.dsl.configure

fun Project.configureDeployGate() {
  with(pluginManager) {
    apply("deploygate")
  }

  deploygate {
    apiToken = propOrDef("APP_DEPLOYGATE_API_TOKEN", "")
    appOwnerName = propOrDef("APP_DEPLOYGATE_ORGANIZATION", "")

    deployments {
      create("develRelease") {
        val appVersionCode = propOrDef("APP_VERSIONCODE", "1").toInt()
        message = "Build for develRelease with build number: $appVersionCode"
      }
    }
  }
}

private fun Project.deploygate(
  action: DeployGateExtension.() -> Unit,
) = extensions.configure<DeployGateExtension>(action)

private fun DeployGateExtension.deployments(
  action: NamedDomainObjectContainer<NamedDeployment>.() -> Unit,
) = deployments(closureOf(action))

fun <T : Any> Project.propOrDef(propertyName: String, defaultValue: T): T {
  @Suppress("UNCHECKED_CAST")
  val propertyValue = properties[propertyName] as T?
  return propertyValue ?: defaultValue
}
