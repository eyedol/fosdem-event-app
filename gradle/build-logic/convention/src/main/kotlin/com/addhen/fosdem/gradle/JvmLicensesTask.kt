// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.AbstractCopyTask
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.register

fun Project.configureJvmLicensesTasks() {
  val xcodeTargetPlatform = providers.environmentVariable("PLATFORM_NAME").orElse("")
  val xcodeTargetArchs = providers.environmentVariable("ARCHS")
    .map { arch -> arch.split(",", " ").filter { it.isNotBlank() } }
    .orElse(emptyList())
  val copyArtifactsTask = tasks.register<AssetCopyTask>(
    "copyJvmLicenseeOutputToResources",
  ) {
    inputFile.set(
      layout.buildDirectory
        .file("reports/licensee/jvm/artifacts.json"),
    )

    outputDirectory.set(
      layout.buildDirectory
        .dir("src/main/resources").get(),
    )
    outputFilename.set("licenses.json")
    dependsOn("licenseeJvm")
  }

  //tasks.named("processResources") {
  //  dependsOn(copyArtifactsTask)
  //}

  tasks.withType(Jar::class.java).named("jvmJar") {
    copyIntoResources(this, copyArtifactsTask)
  }
}

private fun copyIntoResources(
  target: AbstractCopyTask,
  versionFileTask: TaskProvider<AssetCopyTask>,
) {
  target.from(versionFileTask) {
    into("src/main/resources")
  }
}

private fun Project.java(
  action: JavaPluginExtension.() -> Unit,
) = extensions.configure<JavaPluginExtension>(action)
