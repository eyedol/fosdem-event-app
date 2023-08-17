plugins {
  id("findreels.convention.kmp")
  id("findreels.android.library")
  id("findreels.kmp.android.hilt")
  id("com.squareup.sqldelight")
}

android.namespace = "com.findreels.data.sqldelight.database"

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.sqldelightApi)
        implementation(libs.sqlDelight.runtime)
        implementation(libs.sqlDelight.coroutinesExt)
        implementation(libs.coroutines.core)
        implementation(libs.ktor.serialization)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.sqlDelight.android)
        implementation(libs.hilt.android)
      }
    }

    val iosMain by getting {
      dependencies {
        implementation(libs.sqlDelight.native)
        val coroutineCore = libs.coroutines.core.get()
        implementation("${coroutineCore.module.group}:${coroutineCore.module.name}:${coroutineCore.versionConstraint.displayName}") {
          version {
            strictly(libs.versions.coroutines.get())
          }
        }
      }
    }
  }
}
