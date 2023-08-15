import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  `kotlin-dsl`
}

group = "com.findreels.gradle"

repositories {
  google()
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
}

dependencies {
  compileOnly(libs.agp)
  compileOnly(libs.kotlin.pluginGradle)
  compileOnly(libs.hilt.gradlePlugin)
  implementation(libs.spotlessGradlePlugin)
  implementation(libs.mixpanel)
}

gradlePlugin {
  plugins {
    //primitive
    register("androidApplication") {
      id = "findreels.android.application"
      implementationClass = "com.findreels.gradle.primitive.AndroidApplicationPlugin"
    }
    register("androidLibrary") {
      id = "findreels.android.library"
      implementationClass = "com.findreels.gradle.primitive.AndroidPlugin"
    }
    register("androidKotlin") {
      id = "findreels.android.kotlin"
      implementationClass = "com.findreels.gradle.primitive.AndroidKotlinPlugin"
    }
    register("androidCompose") {
      id = "findreels.android.compose"
      implementationClass = "com.findreels.gradle.primitive.AndroidComposePlugin"
    }
    register("androidHilt") {
      id = "findreels.android.hilt"
      implementationClass = "com.findreels.gradle.primitive.AndroidHiltPlugin"
    }
    register("kotlinMpp") {
      id = "findreels.kmp"
      implementationClass = "com.findreels.gradle.primitive.KmpPlugin"
    }
    register("kotlinMppAndroid") {
      id = "findreels.kmp.android"
      implementationClass = "com.findreels.gradle.primitive.KmpAndroidPlugin"
    }
    register("kotlinMppAndroidHilt") {
      id = "findreels.kmp.android.hilt"
      implementationClass = "com.findreels.gradle.primitive.KmpAndroidHiltPlugin"
    }
    register("kotlinMppKotlinSerialization") {
      id = "findreels.kmp.serialization"
      implementationClass = "com.findreels.gradle.primitive.KotlinSerializationPlugin"
    }
    register("kotlinMppIos") {
      id = "findreels.kmp.ios"
      implementationClass = "com.findreels.gradle.primitive.KmpIosPlugin"
    }
    register("sqldelight") {
      id = "findreels.sqldelight"
      implementationClass = "com.findreels.gradle.primitive.SqldelightPlugin"
    }
    register("spotless") {
      id = "findreels.spotless"
      implementationClass = "com.findreels.gradle.primitive.SpotlessPlugin"
    }
    // Conventions
    register("androidFeature") {
      id = "findreels.android.ui.compose.feature"
      implementationClass = "com.findreels.gradle.convention.AndroidUiComposeFeaturePlugin"
    }
    register("kmp") {
      id = "findreels.convention.kmp"
      implementationClass = "com.findreels.gradle.convention.KmpPlugin"
    }
    //build-plugins
    create("buildTime") {
      id = "build-time"
      implementationClass = "com.findreels.gradle.plugin.buildtime.BuildTimePlugin"
    }
  }
}

