plugins {
  id("com.addhen.fosdem.kotlin.multiplatform")
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        api(libs.kotlininject.runtime)
        api(libs.circuit.runtime)
      }
    }

    val jvmMain by getting
  }
}
