// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0


import com.android.build.api.variant.ResValue
import org.gradle.configurationcache.extensions.capitalized

plugins {
  id("com.addhen.fosdem.android.application")
  id("com.addhen.fosdem.kotlin.android")
  id("com.addhen.fosdem.compose")
  id("com.addhen.fosdem.gradle.plugins.deploygate")
  alias(libs.plugins.ksp)
  alias(libs.plugins.moduleGradleAssertion)
}

val useReleaseKeystore = rootProject.file("release/app-release.jks").exists()

val appVersionCode = propOrDef("APP_VERSIONCODE", "1").toInt()
val packageName = "com.addhen.fosdem.android.app"

android {
  namespace = packageName

  defaultConfig {
    applicationId = packageName
    versionCode = appVersionCode
    versionName = "0.1.0-SNAPSHOT" // X.Y.Z; X = Major, Y = minor, Z = Patch level
    setProperty("archivesBaseName", rootProject.name)
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  signingConfigs {
    create("devel") {
      storeFile = rootProject.file("release/app-debug.jks")
      storePassword = "android"
      keyAlias = "androiddebugkey"
      keyPassword = "android"
    }

    if (useReleaseKeystore) {
      create("prod") {
        storeFile = rootProject.file("release/app-release.jks")
        storePassword = propOrDef("APP_RELEASE_KEYSTORE_PWD", "")
        keyAlias = propOrDef("APP_RELEASE_KEYSTORE_ALIAS", "")
        keyPassword = propOrDef("APP_RELEASE_KEY_PWD", "")
      }
    }
  }

  // Enable BuildConfig class
  buildFeatures {
    buildConfig = true
  }

  packaging {
    resources {
      excludes += "META-INF/*.kotlin_module"
      pickFirsts += "META-INF/versions/**/*.bin"
    }
  }

  flavorDimensions += "stage"
  productFlavors {
    create("devel") {
      signingConfig = signingConfigs.getByName("devel")
      isDefault = true
      dimension = "stage"
      applicationIdSuffix = ".devel"
      versionNameSuffix = "-devel"
    }

    create("prod") {
      dimension = "stage"
      // Prod build is the 'release' flavor
      versionCode = (android.defaultConfig.versionCode ?: 0) + 1
      versionName = "0.1.0" // X.Y.Z; X = Major, Y = minor, Z = Patch level
      signingConfig = if (useReleaseKeystore) {
        signingConfigs.getByName("prod")
      } else {
        signingConfigs.getByName("devel")
      }
    }
  }

  buildTypes {

    debug {
      signingConfig = null
    }

    release {
      isShrinkResources = true
      isMinifyEnabled = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  androidComponents {
    beforeVariants(selector().all()) { variant ->
      val isInternal = variant.productFlavors.any { it.first.contains("prod") }
      if (isInternal.not() && variant.name == "prodDebug") {
        variant.enable = false
      }
    }

    onVariants { variant ->
      val appName = if (variant.name == "prodRelease") {
        "FOSDEM Event"
      } else {
        val baseName = "FOSDEM Event"

        val networkFlavor = variant
          .productFlavors
          .first { it.first == "stage" }
        val network = networkFlavor.second
        val buildType = if (variant.buildType == "debug") {
          "-d"
        } else {
          "-r"
        }
        "$baseName-${network.capitalized()}$buildType"
      }

      variant.resValues.put(
        variant.makeResValueKey("string", "app_name"),
        ResValue(appName),
      )
    }
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
    }
  }

  lint {
    warningsAsErrors = true
    // Disable lintVital. Not needed since lint is run on CI
    checkReleaseBuilds = false
    // Ignore any tests
    ignoreTestSources = true
    // Make the build fail on any lint errors
    abortOnError = true
    // Allow lint to check dependencies
    checkDependencies = true
  }
}

dependencies {
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
  implementation(libs.androidx.activity.activity)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.browser)
  implementation(libs.circuit.foundation)
  implementation(libs.ktor.client.okhttp)
  implementation(libs.touchlab.kermit)
  coreLibraryDesugaring(libs.android.desugaring)
  // Third-party libs
  ksp(libs.kotlininject.compiler)
}

ksp {
  arg("me.tatarka.inject.generateCompanionExtensions", "true")
}

moduleGraphAssert {
  maxHeight = 4
}

fun <T : Any> propOrDef(propertyName: String, defaultValue: T): T {
  @Suppress("UNCHECKED_CAST")
  val propertyValue = project.properties[propertyName] as T?
  return propertyValue ?: defaultValue
}
