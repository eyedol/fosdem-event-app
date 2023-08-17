import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlininject.runtime)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.paging)
        implementation(libs.sqldelight.primitive)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.sqldelight.android)
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation(libs.sqldelight.sqlite)
      }
    }

    val iosMain by getting {
      dependencies {
        implementation(libs.sqldelight.native)
      }
    }
  }
}

sqldelight {
  databases {
    create("Database") {
      packageName.set("com.addhen.fosdem.data.sqldelight")
    }
  }
}

tasks.withType<KotlinCompilationTask<*>>().configureEach {
  compilerOptions {
    // Have to disable this as some of the generated code has
    // warnings for unused parameters
    allWarningsAsErrors.set(false)
  }
}

android {
  namespace = "com.addhen.fosdem.data.sqldelight.api"

  defaultConfig {
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }
}
