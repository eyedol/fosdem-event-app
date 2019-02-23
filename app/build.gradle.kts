plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

android {
    compileSdkVersion(Project.COMPILE_SDK)
    dataBinding.isEnabled = true
    defaultConfig {
        applicationId = Project.APPLICATION_ID
        minSdkVersion(Project.MIN_SDK)
        targetSdkVersion(Project.TARGET_SDK)
        versionCode = Project.VERSION_CODE
        versionName = Project.VERSION_NAME
        setProperty("archivesBaseName", "${project.name}-${versionName}")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    dexOptions {
        preDexLibraries = "true" != System.getenv("CI")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isShrinkResources = false
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
            proguardFile(file("proguard-rules.pro"))
        }
    }

    lintOptions {
        setLintConfig(file("${project.projectDir}/lint.xml"))
        disable("AppCompatResource")
        textReport = true
        textOutput("stdout")
    }

    compileOptions {
        setSourceCompatibility(Project.SOURCE_COMPATIBILITY)
        setTargetCompatibility(Project.SOURCE_COMPATIBILITY)
    }
}

dependencies {
    // Kotlin
    implementation(Dependencies.Kotlin.stdlibJvm)
    implementation(Dependencies.Kotlin.coroutines)
    implementation(Dependencies.Kotlin.androidCoroutinesDispatcher)
    // AndroidX
    implementation(Dependencies.AndroidX.appCompat)
    implementation(Dependencies.AndroidX.constraint)
    // AndroidX Navigation
    implementation(Dependencies.AndroidX.Navigation.runtime)
    implementation(Dependencies.AndroidX.Navigation.fragment)
    implementation(Dependencies.AndroidX.Navigation.ui)
    implementation(Dependencies.AndroidX.Navigation.runtimeKtx)
    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    // AndroidX coreKtx
    implementation(Dependencies.AndroidX.coreKtx)
}
