
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
    licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
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
    register("spotless") {
      id = "com.addhen.fosdem.gradle.plugins.spotless"
      implementationClass = "com.addhen.fosdem.gradle.plugins.SpotlessPlugin"
    }
  }
}
