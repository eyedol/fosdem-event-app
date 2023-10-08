// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.theme.tagColors
import kotlinx.collections.immutable.toPersistentList

@MultiThemePreviews
@Composable
fun SessionHeaderPreview() {
  AppTheme {
    Surface {
      SessionHeader()
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
