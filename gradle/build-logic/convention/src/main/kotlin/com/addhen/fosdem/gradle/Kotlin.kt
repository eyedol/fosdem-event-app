// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.gradle

import org.gradle.api.Project

fun Project.configureKotlin() {
  // Configure Java to use our chosen language level. Kotlin will automatically pick this up
  configureJava()
}
