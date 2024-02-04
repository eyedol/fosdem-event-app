// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.addhen.fosdem.compose.common.ui.api.AppImage
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.imageResource
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.ui.about.component.AboutDetail
import com.addhen.fosdem.ui.about.component.AboutFooterLinks
import com.addhen.fosdem.ui.about.component.aboutOthers
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject

const val LicensesScreenTestTag = "LicensesScreen"

@Inject
class LicensesUiFactory : Ui.Factory {
  override fun create(screen: Screen, context: CircuitContext): Ui<*>? = when (screen) {
    is AboutScreen -> {
      ui<LicensesUiState> { state, modifier ->
        About(state, modifier)
      }
    }

    else -> null
  }
}

@Composable
internal fun About(
  uiState: LicensesUiState,
  modifier: Modifier = Modifier,
) {
  val eventSink = uiState.eventSink
  val snackbarHostState = remember { SnackbarHostState() }

  LicensesScreen(
    uiState = uiState,
    snackbarHostState,
    onLinkClick = { url -> eventSink(LicensesUiEvent.GoToLink(url)) },
    contentPadding = PaddingValues(),
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LicensesScreen(
  uiState: LicensesUiState,
  snackbarHostState: SnackbarHostState,
  onLinkClick: (url: String) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  val layoutDirection = LocalLayoutDirection.current
  val appStrings = LocalStrings.current
  Scaffold(
    modifier = Modifier
      .testTag(LicensesScreenTestTag)
      .then(modifier),
    topBar = {
      TopAppBar(
        title = {
          if (scrollBehavior.state.overlappedFraction == 0f) {
            Text(
              text = appStrings.aboutTitle,
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.Medium,
            )
          } else {
            Text(
              text = appStrings.aboutTitle,
              style = MaterialTheme.typography.titleLarge,
              modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
            )
          }
        },
        scrollBehavior = scrollBehavior,
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    contentWindowInsets = WindowInsets(
      left = contentPadding.calculateLeftPadding(layoutDirection),
      top = contentPadding.calculateTopPadding(),
      right = contentPadding.calculateRightPadding(layoutDirection),
      bottom = contentPadding.calculateBottomPadding(),
    ),
    content = { padding ->
      LazyColumn(
        Modifier
          .nestedScroll(scrollBehavior.nestedScrollConnection),
        contentPadding = padding,
      ) {
        item {
          AboutDetail(
            onLinkClick = onLinkClick,
            abountImageResource = imageResource(AppImage.AboutBanner),
          )
        }
        aboutOthers(
          onLicenseItemClick = {
            onAboutItemClick(AboutItem.License)
          },
          onPrivacyPolicyItemClick = {
            onAboutItemClick(AboutItem.PrivacyPolicy(""))
          },
          licenseLabel = appStrings.licenseTitle,
          privacyPolicy = appStrings.privacyPolicyTitle,
        )
        item {
          AboutFooterLinks(
            versionName = uiState.versionName,
          )
        }
      }
    },
  )
}
