// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.session_empty
import com.addhen.fosdem.compose.common.ui.api.session_empty_description
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews
import com.addhen.fosdem.compose.common.ui.api.theme.iconColors
import org.jetbrains.compose.resources.stringResource

@MultiThemePreviews
@Composable
private fun EmptySessionItemsPreview() {
  AppTheme {
    Surface {
      SessionEmptyListView(
        title = stringResource(Res.string.session_empty),
        description = stringResource(Res.string.session_empty_description),
        graphicContent = {
          Icon(
            imageVector = Icons.Filled.HourglassEmpty,
            contentDescription = null,
            modifier = Modifier.size(96.dp),
            tint = iconColors().background,
          )
        },
      )
    }
  }
}
