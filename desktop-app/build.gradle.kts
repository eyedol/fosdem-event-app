// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

import com.addhen.fosdem.gradle.plugins.addKspDependencyForAllTargets
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose")
  alias(libs.plugins.ksp)
  alias(libs.plugins.moduleGradleAssertion)
}

kotlin {
  sourceSets {
    jvmMain {
      dependencies {
        implementation(projects.composeUi.commonApi)
        implementation(projects.featureUi.about)
        implementation(projects.featureUi.map)
        implementation(projects.featureUi.sessionSearch)
        implementation(projects.featureUi.sessionBookmark)
        implementation(projects.featureUi.sessionDetail)
        implementation(projects.featureUi.sessionList)
        implementation(projects.featureUi.main)
        implementation(projects.featureUi.licenses)
        implementation(projects.data.licenses)
        implementation(projects.data.licensesApi)
        implementation(projects.data.events)
        implementation(projects.data.eventsApi)
        implementation(projects.data.rooms)
        implementation(projects.data.roomsApi)
        implementation(projects.data.sqldelight)
        implementation(projects.data.sqldelightApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(projects.coreApi)
        implementation(compose.desktop.currentOs)
        implementation(libs.circuit.foundation)
        // Needed to get ApiService to resolve as it needs HttpClient class from Ktor
        implementation(libs.ktor.client.okhttp)
      }
    }
  }
}

// See https://github.com/JetBrains/compose-multiplatform/tree/master/tutorials/Native_distributions_and_local_execution
compose.desktop {
  application {
    mainClass = "com.addhen.fosdem.desktop.app.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "Fosdem Event App"
      packageVersion = "0.1.0"

      macOS {
        // Due to MacOS can't follow semantic versioning.
        // See https://github.com/JetBrains/compose-multiplatform/issues/2360
        packageVersion = "1.0.0"
      }

      modules("java.sql")

      windows {
        menuGroup = "FOSDEM Event Desktop App"
        // see https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
        upgradeUuid = "BF9CDA6A-1391-46D5-9ED5-383D6E68CCEB"
      }
    }
  }
}

addKspDependencyForAllTargets(libs.kotlininject.compiler)

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

moduleGraphAssert {
  maxHeight = 4
}
