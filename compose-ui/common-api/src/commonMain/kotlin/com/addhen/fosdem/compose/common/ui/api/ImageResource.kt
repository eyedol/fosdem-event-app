// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun imageVectorResource(
  resource: ImageVectorResource,
): ImageVector = vectorResource(resource.drawableResource)

enum class ImageVectorResource(val drawableResource: DrawableResource) {
  FosdemLogo(Res.drawable.fosdem_logo),
  InstagramLogo(Res.drawable.fosdem_logo),
  MastadonLogo(Res.drawable.fosdem_logo),
  XLogo(Res.drawable.fosdem_logo),
  FacebookLogo(Res.drawable.fosdem_logo),
  AboutBanner(Res.drawable.fosdem_logo),
  FosdemCampusMap(Res.drawable.fosdem_campus_map),
}
