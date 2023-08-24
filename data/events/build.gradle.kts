plugins {
  id("com.addhen.fosdem.android.library")
  id("com.addhen.fosdem.kotlin.multiplatform")
  alias(libs.plugins.sqldelight)
}

android.namespace = "com.addhen.fosdem.data.events.api"

kotlin {
  sourceSets {
    all {
      languageSettings.apply {
        optIn("kotlin.RequiresOptIn")
        optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
      }
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(projects.coreApi)
        implementation(projects.data.eventsApi)
        implementation(projects.data.modelApi)
        implementation(projects.data.sqldelightApi)
        implementation(libs.kotlinx.datetime)
        implementation(libs.sqldelight.coroutines)
        implementation(libs.sqldelight.primitive)
      }
    }

    val commonTest by getting {
    }

    // Because Java has great tools for testing. The idea is to test common code on
    // the JVM and if something requires platform specific, test is on the specific platform.
    jvmTest {
      dependencies {
        // Temp. Hardcoded here because it won't work with libs.versions.toml file
        // even declared as junit-bom = { module = "org.junit:junit-bom", version.ref = "junit5"}
        implementation(platform("org.junit:junit-bom:5.10.0"))
        implementation(libs.junit.jupiter)
        implementation(libs.bundles.common.test)
        implementation(libs.ktor.client.mock)
        implementation(libs.coroutines.test)
      }
    }
  }
}
