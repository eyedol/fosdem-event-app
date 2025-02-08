// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.search_term_place_holder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextFieldAppBar(
  searchQuery: String,
  onSearchQueryChanged: (String) -> Unit,
  testTag: String,
  modifier: Modifier = Modifier,
) {
  TopAppBar(
    modifier = modifier,
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
    title = {
      SearchTextField(
        searchQuery = searchQuery,
        onSearchQueryChanged = onSearchQueryChanged,
        modifier = Modifier
          .testTag(testTag),
      )
    },
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTextField(
  modifier: Modifier = Modifier,
  searchQuery: String = "",
  onSearchQueryChanged: (String) -> Unit = {},
  enabled: Boolean = true,
  interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
) {
  val focusRequester = remember { FocusRequester() }
  val keyboardController = LocalSoftwareKeyboardController.current
  var query by remember { mutableStateOf(searchQuery) }

  BasicTextField(
    value = query,
    onValueChange = { value ->
      query = value
      onSearchQueryChanged(value)
    },
    modifier = modifier
      .fillMaxWidth(1.0f)
      .focusRequester(focusRequester),
    enabled = enabled,
    textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface),
    cursorBrush = SolidColor(MaterialTheme.colorScheme.onSurface),
    singleLine = true,
    keyboardOptions = KeyboardOptions.Default,
    keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
    decorationBox = @Composable { innerTextField ->
      TextFieldDefaults.DecorationBox(
        value = query,
        innerTextField = innerTextField,
        enabled = enabled,
        singleLine = true,
        visualTransformation = VisualTransformation.None,
        interactionSource = interactionSource,
        placeholder = {
          if (searchQuery.isBlank()) {
            Text(
              text = stringResource(Res.string.search_term_place_holder),
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSurface,
            )
          }
        },
        trailingIcon = {
          if (searchQuery.isNotEmpty()) {
            Box(modifier = Modifier.offset(x = (-4).dp)) {
              IconButton(
                onClick = {
                  query = ""
                  onSearchQueryChanged("")
                  // This is mostly for iOS, otherwise there is no way to dismiss the iOS
                  // keyboard once opened.
                  keyboardController?.hide()
                },
              ) {
                Icon(
                  imageVector = Icons.Default.Clear,
                  contentDescription = null,
                  tint = MaterialTheme.colorScheme.onSurface,
                )
              }
            }
          }
        },
        contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(0.dp),
        container = {},
      )
    },
  )
}
