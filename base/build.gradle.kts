plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(Project.COMPILE_SDK)
    dataBinding.isEnabled = true
    defaultConfig {
        minSdkVersion(Project.MIN_SDK)
    }
    lintOptions {
        disable("GradleCompatible")
    }
}

dependencies {
    api(Dependencies.Kotlin.stdlibJvm)
    api(Dependencies.Kotlin.coroutines)
    api(Dependencies.Kotlin.androidCoroutinesDispatcher)
    // AndroidX
    api(Dependencies.AndroidX.appCompat)
    api(Dependencies.AndroidX.design)
    api(Dependencies.AndroidX.recyclerView)
    api(Dependencies.AndroidX.constraint)
    api(Dependencies.AndroidX.coreKtx)
    // AndroidX Navigation
    api(Dependencies.AndroidX.Navigation.runtime)
    api(Dependencies.AndroidX.Navigation.fragment)
    api(Dependencies.AndroidX.Navigation.ui)
    api(Dependencies.AndroidX.Navigation.runtimeKtx)
    api(Dependencies.AndroidX.Navigation.fragmentKtx)
    api(Dependencies.AndroidX.Navigation.uiKtx)
    // AndroidX coreKtx
    implementation(Dependencies.AndroidX.coreKtx)
    api(Dependencies.Glide.core)
    api(Dependencies.Glide.okhttp3)
    // Architecture components lifecycle
    api(Dependencies.AndroidX.Lifecycle.runtime)
    api(Dependencies.AndroidX.Lifecycle.extensions)
    // Utility
    api(Dependencies.Dagger.core)
    api(Dependencies.Dagger.android)
    api(Dependencies.Dagger.support)
    api(Dependencies.timber)
    kapt(Dependencies.Databinding.compiler)
    kapt(Dependencies.AndroidX.Lifecycle.compiler)
    kapt(Dependencies.Glide.compiler)
}
