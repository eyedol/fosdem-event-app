val kotlin_version: String by extra
/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(Project.COMPILE_SDK)
    defaultConfig {
        minSdkVersion(Project.MIN_SDK)
        targetSdkVersion(Project.TARGET_SDK)
        javaCompileOptions {
            annotationProcessorOptions {
                arguments = mapOf("room.schemaLocation" to "$projectDir/schemas")
            }
        }
        sourceSets {
            getByName("androidTest").assets.srcDirs("$projectDir/schemas")
        }
    }
    lintOptions {
        disable("GradleCompatible", "ObsoleteLintCustomCheck")
    }
}

dependencies {
    api(project(":data:model"))
    api(Dependencies.Kotlin.stdlibJvm)
    api(Dependencies.Kotlin.coroutines)
    api(Dependencies.AndroidX.Room.runtime)
    api(Dependencies.AndroidX.Room.coroutine)
    api(Dependencies.AndroidX.Lifecycle.liveData)
    implementation(Dependencies.Dagger.core)
    implementation(Dependencies.Dagger.support)
    kapt(Dependencies.AndroidX.Room.compiler)
    kapt(Dependencies.Databinding.compiler)
    kapt(Dependencies.Dagger.compiler)
    kapt(Dependencies.Dagger.processor)
    testImplementation(Dependencies.Test.junit)
}
