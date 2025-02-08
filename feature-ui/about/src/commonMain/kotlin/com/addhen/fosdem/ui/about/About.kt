// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.about

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
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.about_title
import com.addhen.fosdem.compose.common.ui.api.license_title
import com.addhen.fosdem.compose.common.ui.api.privacy_policy_title
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.ui.about.component.AboutDetail
import com.addhen.fosdem.ui.about.component.AboutFooterLinks
import com.addhen.fosdem.ui.about.component.aboutOthers
import com.slack.circuit.runtime.CircuitContext
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.runtime.ui.ui
import me.tatarka.inject.annotations.Inject
import org.jetbrains.compose.resources.stringResource

const val AboutScreenTestTag = "AboutScreen"

private const val PRIVACY_POLICY_URL =
  "https://eyedol.github.io/fosdem-event-app/privacy-policy.html"

@Inject
class AboutUiFactory : Ui.Factory {
  override fun create(
    screen: Screen,
    context: CircuitContext,
  ): Ui<*>? =
    when (screen) {
      is AboutScreen -> {
        ui<AboutUiState> { state, modifier ->
          About(state, modifier)
        }
      }

      else -> null
    }
}

@Composable
internal fun About(
  uiState: AboutUiState,
  modifier: Modifier = Modifier,
) {
  val eventSink = uiState.eventSink
  val snackbarHostState = remember { SnackbarHostState() }

  AboutScreen(
    uiState = uiState,
    snackbarHostState,
    onLinkClick = { url -> eventSink(AboutUiEvent.GoToLink(url)) },
    onAboutItemClick = { aboutItem -> eventSink(AboutUiEvent.GoToAboutItem(aboutItem)) },
    contentPadding = PaddingValues(),
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AboutScreen(
  uiState: AboutUiState,
  snackbarHostState: SnackbarHostState,
  onAboutItemClick: (AboutItem) -> Unit,
  onLinkClick: (url: String) -> Unit,
  contentPadding: PaddingValues,
  modifier: Modifier,
) {
  val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
  val layoutDirection = LocalLayoutDirection.current

  Scaffold(
    modifier =
    Modifier
      .testTag(AboutScreenTestTag)
      .then(modifier),
    topBar = {
      TopAppBar(
        title = {
          if (scrollBehavior.state.overlappedFraction == 0f) {
            Text(
              text = stringResource(Res.string.about_title),
              style = MaterialTheme.typography.headlineLarge,
              fontWeight = FontWeight.Medium,
            )
          } else {
            Text(
              text = stringResource(Res.string.about_title),
              style = MaterialTheme.typography.titleLarge,
              modifier = Modifier.alpha(scrollBehavior.state.overlappedFraction),
            )
          }
        },
        scrollBehavior = scrollBehavior,
      )
    },
    snackbarHost = { SnackbarHost(snackbarHostState) },
    contentWindowInsets =
    WindowInsets(
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
            imageResource = ImageVectorResource.AboutBanner,
          )
        }
        aboutOthers(
          onLicenseItemClick = {
            onAboutItemClick(AboutItem.License)
          },
          onPrivacyPolicyItemClick = {
            onAboutItemClick(AboutItem.PrivacyPolicy(PRIVACY_POLICY_URL))
          },
          licenseLabel = Res.string.license_title,
          privacyPolicy = Res.string.privacy_policy_title,
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
