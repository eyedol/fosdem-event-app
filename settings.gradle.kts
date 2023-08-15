pluginManagement {
  includeBuild("gradle/build-logic")
  repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
  }
}

include(
    ":app",
    ":feature:home",
    ":feature:session",
    ":base",
    ":data:db",
    ":data:repository",
    ":data:model",
    ":data:api",
    ":platform:parser",
    ":platform:extension"
)
