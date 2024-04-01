// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import fosdem.compose_ui.common_api.generated.resources.Res
import fosdem.compose_ui.common_api.generated.resources.fosdem_logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun imageVectorResource(
  resource: ImageVectorResource,
): ImageVector = vectorResource(resource.drawableResource)

@OptIn(ExperimentalResourceApi::class)
enum class ImageVectorResource(val drawableResource: DrawableResource) {
  FosdemLogo(Res.drawable.fosdem_logo),
  InstagramLogo(Res.drawable.fosdem_logo),
  MastadonLogo(Res.drawable.fosdem_logo),
  XLogo(Res.drawable.fosdem_logo),
  FacebookLogo(Res.drawable.fosdem_logo),
  AboutBanner(Res.drawable.fosdem_logo),
}
