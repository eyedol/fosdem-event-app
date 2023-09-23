// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable

expect class ImageResource

@Composable
fun imageResource(appImage: AppImage): ImageResource = appImage.asImageResource()

@Composable
expect fun AppImage.asImageResource(): ImageResource

enum class AppImage {
  FosdemLogo,
}
