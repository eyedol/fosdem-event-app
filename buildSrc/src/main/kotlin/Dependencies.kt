object Dependencies {

    object GradlePlugin {
        val android = "com.android.tools.build:gradle:3.4.0-beta05"
        val r8 = "com.android.tools:r8:1.3.52"
        val kotlin = Kotlin.version
        val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
        val navigationSafeArgs =
            "android.arch.navigation:navigation-safe-args-gradle-plugin:${AndroidX.Navigation.version}"
        val jetifier = "com.android.tools.build.jetifier:jetifier-processor:1.0.0-beta02"
        val crashlytics = "io.fabric.tools:gradle:1.26.1"
    }

    object Kotlin {
        val version = "1.3.20"
        val stdlibJvm = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"

        val coroutinesVersion = "1.1.1"
        val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        val androidCoroutinesDispatcher =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object AndroidX {
        val jetifier = "com.android.tools.build.jetifier:jetifier-core:1.0.0-beta02"
        val appCompat = "androidx.appcompat:appcompat:1.0.0"
        val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
        val constraint = "androidx.constraintlayout:constraintlayout:2.0.0-alpha3"
        val design = "com.google.android.material:material:1.1.0-alpha02"
        val coreKtx = "androidx.core:core-ktx:1.0.0-alpha1"
        val preference = "androidx.preference:preference:1.0.0"
        val fragment = "androidx.fragment:fragment:1.1.0-alpha03"

        val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.0.0"
        val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata:2.0.0"

        object Room {
            val version = "2.1.0-alpha03"
            val compiler = "androidx.room:room-compiler:$version"
            val runtime = "androidx.room:room-runtime:$version"
            val coroutine = "androidx.room:room-coroutines:$version"
        }

        object Navigation {
            val version = "1.0.0-alpha08"
            val runtime = "android.arch.navigation:navigation-runtime:$version"
            val runtimeKtx = "android.arch.navigation:navigation-runtime-ktx:$version"
            val fragment = "android.arch.navigation:navigation-fragment:$version"
            val ui = "android.arch.navigation:navigation-ui:1.0.0-alpha10"
            val fragmentKtx = "android.arch.navigation:navigation-fragment-ktx:$version"
            val uiKtx = "android.arch.navigation:navigation-ui-ktx:1.0.0-alpha10"
        }

        object Work {
            val version = "1.0.0-alpha12"
            val runtime = "android.arch.work:work-runtime:$version"
            val runtimeKtx = "android.arch.work:work-runtime-ktx:$version"
        }
    }
}