plugins {
  alias(libs.plugins.kotlin.jvm)
}

dependencies {
  implementation(projects.coreApi)
  implementation(projects.data.coreApi)
  implementation(projects.data.sqldelightApi)
  implementation(projects.data.modelApi)
  implementation(libs.kotlinx.datetime)
  implementation(libs.sqldelight.sqlite)
  // Temp. Hardcoded here because it won't work with libs.versions.toml file
  // even declared as junit-bom = { module = "org.junit:junit-bom", version.ref = "junit5"}
  api(platform("org.junit:junit-bom:5.10.0"))
  api(libs.junit.jupiter)
  api(libs.ktor.client.mock)
  api(libs.coroutines.test)
}

