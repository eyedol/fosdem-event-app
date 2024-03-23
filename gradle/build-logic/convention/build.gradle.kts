plugins {
  `kotlin-dsl`
  alias(libs.plugins.spotless)
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(21))
  }
}

spotless {
  kotlin {
    target("src/**/*.kt")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("../../spotless/copyright.txt"))
  }
  kotlinGradle {
    target("*.kts")
    ktlint(libs.versions.ktlint.get())
    licenseHeaderFile(rootProject.file("../../spotless/copyright.txt"), "(^(?![\\/ ]\\**).*$)")
  }
}

dependencies {
  compileOnly(libs.android.gradlePlugin)
  compileOnly(libs.kotlin.gradlePlugin)
  compileOnly(libs.spotless.gradlePlugin)
  compileOnly(libs.compose.gradlePlugin)
  compileOnly(libs.licensee.gradlePlugin)
}

gradlePlugin {
  plugins {

    register("kotlinMultiplatform") {
      id = "com.addhen.fosdem.kotlin.multiplatform"
      implementationClass = "com.addhen.fosdem.gradle.plugins.KotlinMultiplatformConventionPlugin"
    }

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

    register("kotlinAndroid") {
      id = "com.addhen.fosdem.kotlin.android"
      implementationClass = "com.addhen.fosdem.gradle.plugins.KotlinAndroidConventionPlugin"
    }

    register("compose") {
      id = "com.addhen.fosdem.compose"
      implementationClass = "com.addhen.fosdem.gradle.plugins.ComposeMultiplatformConventionPlugin"
    }

    register("composeAndroid") {
      id = "com.addhen.fosdem.compose.android"
      implementationClass = "com.addhen.fosdem.gradle.plugins.AndroidComposeConventionPlugin"
    }

    register("spotless") {
      id = "com.addhen.fosdem.gradle.plugins.spotless"
      implementationClass = "com.addhen.fosdem.gradle.plugins.SpotlessConventionPlugin"
    }

    register("kotlinMppKotlinSerialization") {
      id = "com.addhen.fosdem.kmp.serialization"
      implementationClass = "com.addhen.fosdem.gradle.plugins.KotlinSerializationConventionPlugin"
    }
  }
}
