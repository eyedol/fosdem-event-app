// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.search.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

const val DropdownFilterChipItemTestTag = "DropdownFilterChipItem"

@Composable
fun <T> DropdownFilterChip(
  searchFilterUiState: SearchFilterUiState<T>,
  onSelected: (T, Boolean) -> Unit,
  filterChipLabel: @Composable () -> Unit,
  dropdownMenuItemText: @Composable (T) -> Unit,
  modifier: Modifier = Modifier,
  filterChipLeadingIcon: @Composable (() -> Unit)? = null,
  filterChipTrailingIcon: @Composable (() -> Unit)? = null,
  onFilterChipClick: (() -> Unit)? = null,
  dropdownMenuItemLeadingIcon: @Composable (() -> Unit)? = null,
  dropdownMenuItemTrailingIcon: @Composable ((T) -> Unit)? = null,
) {
  var expanded by remember { mutableStateOf(false) }
  val onSelectedUpdated by rememberUpdatedState(newValue = onSelected)

  val expandMenu = { expanded = true }
  val collapseMenu = { expanded = false }

  val selectedItems = searchFilterUiState.selectedItems

  Box(
    modifier = modifier.wrapContentSize(Alignment.TopStart),
  ) {
    FilterChip(
      selected = searchFilterUiState.isSelected,
      onClick = {
        onFilterChipClick?.invoke()
        expandMenu()
      },
      label = filterChipLabel,
      leadingIcon = filterChipLeadingIcon,
      trailingIcon = filterChipTrailingIcon,
    )
    DropdownMenu(
      expanded = expanded,
      onDismissRequest = collapseMenu,
    ) {
      searchFilterUiState.items.forEachIndexed { index, item ->

        val leadingIcon: @Composable (() -> Unit)? = if (selectedItems.contains(item)) {
          dropdownMenuItemLeadingIcon?.let { icon ->
            {
              icon()
            }
          }
        } else {
          null
        }
        DropdownMenuItem(
          text = {
            dropdownMenuItemText(item)
          },
          leadingIcon = leadingIcon,
          trailingIcon = dropdownMenuItemTrailingIcon?.let { icon ->
            {
              icon(item)
            }
          },
          onClick = {
            onSelectedUpdated(
              item,
              selectedItems.contains(item).not(),
            )
            collapseMenu()
          },
          modifier = Modifier.testTag(DropdownFilterChipItemTestTag),
        )

        // Add a horizontal divider for all items except the last one
        if (index < searchFilterUiState.items.lastIndex) {
          HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
          )
        }
      }
    }
  }
}

@Composable
fun <T> DropdownFilterChip(
  searchFilterUiState: SearchFilterUiState<T>,
  onSelected: (T, Boolean) -> Unit,
  filterChipLabelDefaultText: String,
  dropdownMenuItemText: (T) -> String,
  modifier: Modifier = Modifier,
  onFilterChipClick: (() -> Unit)? = null,
) {
  DropdownFilterChip(
    modifier = modifier,
    searchFilterUiState = searchFilterUiState,
    onSelected = onSelected,
    filterChipLabel = {
      Text(
        text = searchFilterUiState.selectedValues.ifEmpty {
          filterChipLabelDefaultText
        },
      )
    },
    filterChipTrailingIcon = {
      Icon(
        imageVector = Icons.Default.ArrowDropDown,
        contentDescription = null,
      )
    },
    onFilterChipClick = onFilterChipClick,
    dropdownMenuItemText = { item ->
      Text(
        text = dropdownMenuItemText(item),
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
    dropdownMenuItemLeadingIcon = {
      Icon(
        imageVector = Icons.Default.Check,
        contentDescription = null,
        modifier = Modifier.size(24.dp),
        tint = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    },
  )
}
