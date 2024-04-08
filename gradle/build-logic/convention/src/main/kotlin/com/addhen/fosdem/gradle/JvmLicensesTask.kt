// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import org.gradle.api.Project
import org.gradle.kotlin.dsl.register

fun Project.configureJvmLicensesTasks() {
  val copyArtifactsTask = tasks.register<AssetCopyTask>(
    "copyJvmLicenseeOutputToResources",
  ) {
    inputFile.set(
      layout.buildDirectory
        .file("reports/licensee/jvm/artifacts.json"),
    )

    outputDirectory.set(
      layout.buildDirectory.dir("processedResources/jvm/main"),
    )
    outputFilename.set("licenses.json")
    dependsOn("licenseeJvm")
  }

  val jvmJarTask = project.tasks.named("jvmJar")
  jvmJarTask.configure { dependsOn(copyArtifactsTask) }
}
