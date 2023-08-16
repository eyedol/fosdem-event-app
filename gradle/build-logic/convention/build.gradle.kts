plugins {
  `kotlin-dsl`
  alias(libs.plugins.spotless)
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

repositories {
  google()
  mavenCentral()
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("spotless/copyright.txt"))
  }
  kotlinGradle {
    target("*.kts")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("spotless/copyright.kt"), "(^(?![\\/ ]\\**).*$)")
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.spotless.gradlePlugin)
  compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
  plugins {

    register("root") {
      id = "com.addhen.fosdem.gradle.plugins.root"
      implementationClass = "com.addhen.fosdem.gradle.plugins.RootConventionPlugin"
    }

    register("androidApplication") {
      id = "com.addhen.fosdem.android.application"
      implementationClass = "com.addhen.fosdem.gradle.plugins.AndroidApplicationConventionPlugin"
    }

    register("androidLibrary") {
      id = "com.addhen.fosdem.android.library"
      implementationClass = "com.addhen.fosdem.gradle.plugins.AndroidLibraryConventionPlugin"
    }

    register("spotless") {
      id = "com.addhen.fosdem.gradle.plugins.spotless"
      implementationClass = "com.addhen.fosdem.gradle.plugins.SpotlessConventionPlugin"
    }
  }
}
