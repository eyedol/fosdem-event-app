plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(Project.COMPILE_SDK)
    dataBinding.isEnabled = true
    defaultConfig {
        minSdkVersion(Project.MIN_SDK)
    }
    lintOptions {
        disable("GradleCompatible", "ObsoleteLintCustomCheck")
    }
}

dependencies {
    implementation(project(":base"))
    api(project(":data:repository"))
    // Tests
    testImplementation(Dependencies.Test.junit)
    kapt(Dependencies.Databinding.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.processor)
}
