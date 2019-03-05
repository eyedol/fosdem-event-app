plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

val env = "env"
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
        disable("AppCompatResource", "ObsoleteLintCustomCheck")
        textReport = true
        textOutput("stdout")
    }

    compileOptions {
        setSourceCompatibility(Project.SOURCE_COMPATIBILITY)
        setTargetCompatibility(Project.SOURCE_COMPATIBILITY)
    }

    flavorDimensions(env)

    productFlavors {
        create("development") {
            setDimension(env)
            applicationId = "${Project.APPLICATION_ID}.dev"
            testApplicationId = "${Project.APPLICATION_ID}dev.test"
        }

        create("production") {
            setDimension(env)
        }
    }
}

dependencies {
    // Kotlin
    implementation(project(":feature:home"))

    kapt(Dependencies.Databinding.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.processor)

}
