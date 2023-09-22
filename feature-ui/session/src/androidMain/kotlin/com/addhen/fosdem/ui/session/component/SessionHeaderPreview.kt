// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.ImageResource
import com.addhen.fosdem.compose.common.ui.api.painterResource
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.theme.tagColors
import com.addhen.fosdem.ui.session.R
import kotlinx.collections.immutable.toPersistentList

@MultiThemePreviews
@Composable
fun SessionHeaderPreview() {
  AppTheme {
    Surface {
      SessionHeader(
        tile = "FOSDEM",
        year = "24",
        location = "@ Brussels, Belgium",
        tags = tags(),
        painter = painterResource(
          ImageResource(R.drawable.fosdem_logo),
        ),
      )
    }
  }
}

@Composable
private fun tags() = listOf(
  Tag("beer", tagColors().tagColorMain),
  Tag("open source", tagColors().tagColorAlt),
  Tag("free software", tagColors().tagColorAlt),
  Tag("lightning talks", tagColors().tagColorMain),
  Tag("devrooms", tagColors().tagColorMain),
  Tag("800+ talks", tagColors().tagColorAlt),
  Tag("8000+ hackers", tagColors().tagColorAlt),
).toPersistentList()
