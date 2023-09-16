// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
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
  // Makes it possible to run the app from IDE run menu
  jvm {
    withJava()
  }

  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(projects.featureUi.session)
        implementation(projects.featureUi.main)
        implementation(projects.composeUi.commonApi)
        implementation(projects.data.events)
        implementation(projects.data.eventsApi)
        implementation(projects.data.sqldelight)
        implementation(projects.data.sqldelightApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.coreApi)
        implementation(projects.coreApi)
        implementation(compose.desktop.currentOs)
        implementation(libs.circuit.foundation)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "com.addhen.fosdem.desktop.app.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "FOSDEM"
      packageVersion = "1.0.0"

      modules("java.sql")

      windows {
        menuGroup = "FOSDEM Desktop App"
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
