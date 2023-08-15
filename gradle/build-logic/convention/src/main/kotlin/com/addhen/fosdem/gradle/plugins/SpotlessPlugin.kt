package com.addhen.fosdem.gradle.plugins

import com.addhen.fosdem.gradle.libs
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

@Suppress("unused")
class SpotlessPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply("com.diffplug.spotless")

      extensions.configure<SpotlessExtension> {
        kotlin {
          target("**/*.kt")
          targetExclude("$buildDir/**/*.kt")
          targetExclude("bin/**/*.kt")
          ktlint(libs.findVersion("ktlint").get().requiredVersion)
            .userData(mapOf("android" to "true"))
            .editorConfigOverride(
              mapOf("ij_kotlin_imports_layout" to "*,java.**,javax.**,kotlin.**,^")
            )
          trimTrailingWhitespace()
          indentWithSpaces()
          endWithNewline()
          licenseHeaderFile(project.rootProject.file("spotless/copyright.kt"))
        }
        format("kts") {
          target("**/*.kts")
          targetExclude("**/build/**/*.kts")
        }
        format("xml") {
          target("**/*.xml")
          targetExclude("**/build/**/*.xml")
        }
      }

      afterEvaluate {
        if (tasks.findByName("check")?.enabled == true) {
          tasks.getByName("check").dependsOn("spotlessCheck")
        }
      }
    }
  }
}
