package com.addhen.fosdem.core.api

data class ApplicationInfo(
  val packageName: String,
  val debugBuild: Boolean,
  val flavor: Flavor,
  val versionName: String,
  val versionCode: Int,
)

enum class Flavor {
  Qa, Standard
}
