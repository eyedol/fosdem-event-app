enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }

  versionCatalogs {
    create("libs") {
      from(files("../libs.versions.toml"))
    }
  }
}

rootProject.name = "build-logic"
include(":convention")
