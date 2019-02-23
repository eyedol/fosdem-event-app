buildscript {
    repositories {
        google()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
    }
    dependencies {
        classpath(Dependencies.GradlePlugin.r8)
        classpath(Dependencies.GradlePlugin.android)
        classpath(kotlin("gradle-plugin", version = Dependencies.GradlePlugin.kotlin))
        classpath(Dependencies.GradlePlugin.navigationSafeArgs)
        classpath(Dependencies.GradlePlugin.kotlinSerialization)
        classpath(Dependencies.GradlePlugin.jetifier)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
        // Using glide's snapshot release for a fix for Jetpack because
        // the stable version of Glide doesn't use Jetpack's namespacess
        // even though Jetifier is enabled.
        maven(url = "http://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "http://dl.bintray.com/kotlin/kotlin-eap")
    }
}

task<Delete>("Delete") {
    delete(rootProject.buildDir)
}