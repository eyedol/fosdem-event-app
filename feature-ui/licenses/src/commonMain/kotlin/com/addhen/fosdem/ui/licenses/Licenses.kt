// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.licenses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.LocalStrings
import com.addhen.fosdem.core.api.screens.AboutScreen
import com.addhen.fosdem.ui.licenses.component.Preference
import com.addhen.fosdem.ui.licenses.component.PreferenceHeader
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
    onNavigationIconClick = { eventSink(LicensesUiEvent.NavigateUp) },
    onLinkClick = { url -> eventSink(LicensesUiEvent.GoToLink(url)) },
    contentPadding = PaddingValues(),
    modifier = modifier,
  )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun LicensesScreen(
  uiState: LicensesUiState,
  snackbarHostState: SnackbarHostState,
  onNavigationIconClick: () -> Unit,
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
          Text(
            text = appStrings.openSourceLicenses,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Medium,
          )
        },
        navigationIcon = {
          IconButton(onClick = onNavigationIconClick) {
            Icon(
              imageVector = Icons.Filled.ArrowBack,
              contentDescription = LocalStrings.current.openSourceLicenses,
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
        modifier = Modifier
          .padding(padding)
          .fillMaxWidth(),
      ) {
        uiState.licenses.forEach { group ->
          stickyHeader {
            PreferenceHeader(
              title = "",
              modifier = Modifier.fillMaxSize(),
              tonalElevation = 1.dp,
            )
          }

          items(group.artifacts) { artifact ->
            Preference(
              title = (artifact.name ?: artifact.artifactId),
              summary = {
                Column {
                  Text("${artifact.artifactId} v${artifact.version}")

                  artifact.spdxLicenses?.forEach { license ->
                    Text(license.name)
                  }
                }
              },
              modifier = Modifier.clickable {
                onLinkClick("artifact")
              },
            )
          }
        }
      }
    },
  )
}
