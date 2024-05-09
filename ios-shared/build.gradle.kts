// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import com.addhen.fosdem.gradle.plugins.addKspDependencyForAllTargets
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  id("com.addhen.fosdem.kotlin.multiplatform")
  id("com.addhen.fosdem.compose")
  alias(libs.plugins.ksp)
  alias(libs.plugins.moduleGradleAssertion)
}

kotlin {

  sourceSets {
    commonMain {
      dependencies {
        api(projects.composeUi.commonApi)
        api(projects.featureUi.about)
        api(projects.featureUi.map)
        api(projects.featureUi.sessionSearch)
        api(projects.featureUi.sessionBookmark)
        api(projects.featureUi.sessionDetail)
        api(projects.featureUi.sessionList)
        api(projects.featureUi.main)
        api(projects.featureUi.licenses)
        api(projects.data.licenses)
        api(projects.data.licensesApi)
        api(projects.data.events)
        api(projects.data.eventsApi)
        api(projects.data.rooms)
        api(projects.data.roomsApi)
        api(projects.data.sqldelight)
        api(projects.data.modelApi)
        api(projects.data.coreApi)
        api(projects.coreApi)
        api(libs.circuit.foundation)
        implementation(projects.data.sqldelightApi)
      }
    }

    targets.withType<KotlinNativeTarget>().configureEach {
      binaries.framework {
        isStatic = true
        baseName = "FosdemKt"

        export(projects.featureUi.main)
        export(projects.composeUi.commonApi)
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
