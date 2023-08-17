plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.sqldelight.database"

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.data.sqldelightApi)
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
