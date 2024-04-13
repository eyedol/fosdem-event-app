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
  api(libs.kotlin.test.junit5)
  api(libs.ktor.client.mock)
  api(libs.coroutines.test)
}

