package com.addhen.fosdem.ui.session.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.addhen.fosdem.compose.common.ui.api.theme.AppTheme
import com.addhen.fosdem.compose.common.ui.api.theme.MultiThemePreviews

@MultiThemePreviews
@Composable
fun SessionHeaderPreview() {
  AppTheme {
    Surface {
      /*
      TODO add a banner resource
      SessionHeader(
        painter = painterResource(ImageResource()),
      )*/
    }
  }
}
