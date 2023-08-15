package com.addhen.fosdem.gradle

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

fun Project.androidApplication(action: BaseAppModuleExtension.() -> Unit) {
  extensions.configure(action)
}

fun Project.androidLibrary(action: LibraryExtension.() -> Unit) {
  extensions.configure(action)
}

fun Project.android(action: TestedExtension.() -> Unit) {
  extensions.configure(action)
}

fun Project.setupAndroid() {
  android {
    namespace?.let {
      this.namespace = it
    }
    compileSdkVersion(libs.findVersion("compileSdk").get().toString().toInt())

    defaultConfig {
      minSdk = libs.findVersion("minSdk").get().toString().toInt()

      versionCode = 1
      versionName = "0.1.0"
    }

    // FIXME: lint task was complaining about a bug in lint and suggested to disable this
    // check
    lintOptions {
      disable += "DialogFragmentCallbacksDetector"
      disable += "ObsoleteLintCustomCheck"
    }

    compileOptions {
      sourceCompatibility = JavaVersion.VERSION_17
      targetCompatibility = JavaVersion.VERSION_17
      isCoreLibraryDesugaringEnabled = true
    }

    dependencies {
      add("coreLibraryDesugaring", libs.findLibrary("android.desugaring").get())
    }
    testOptions {
      unitTests {
        isIncludeAndroidResources = true
      }
    }

    defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
  }
}
