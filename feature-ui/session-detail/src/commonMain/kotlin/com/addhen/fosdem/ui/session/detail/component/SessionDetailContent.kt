// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.detail.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.ClickableLinkText
import com.addhen.fosdem.compose.common.ui.api.theme.md_theme_light_outline
import com.addhen.fosdem.model.api.Speaker
import kotlinx.collections.immutable.PersistentList

const val SessionItemDetailReadMoreButtonTestTag = "SessionItemDetailReadMoreButtonTestTag"

@Composable
fun SessionDetailContent(
  uiState: SessionDetailItemSectionUiState,
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {
  val descriptionText = uiState.event.description
  val abstractText = uiState.event.abstractText
  val description = when {
    descriptionText.isBlank().not() && abstractText.isBlank().not() -> {
      "${abstractText}\n\n$descriptionText"
    }
    descriptionText.isBlank().not() -> {
      descriptionText
    }
    else -> {
      abstractText
    }
  }
  Column(modifier = modifier) {
    DescriptionSection(
      readMore = uiState.readMoreTitle,
      description = description,
      onLinkClick = onLinkClick,
    )
  }
}

@Composable
private fun DescriptionSection(
  readMore: String,
  description: String,
  modifier: Modifier = Modifier,
  onLinkClick: (url: String) -> Unit,
) {
  var isExpanded by rememberSaveable { mutableStateOf(false) }

  SelectionContainer {
    Column(modifier = modifier.animateContentSize()) {
      ClickableLinkText(
        style = MaterialTheme.typography.bodyLarge
          .copy(color = MaterialTheme.colorScheme.onSurface),
        content = description,
        onLinkClick = onLinkClick,
        regex = "(https)(://[\\w/:%#$&?()~.=+\\-]+)".toRegex(),
        overflow = TextOverflow.Ellipsis,
        maxLines = if (isExpanded) Int.MAX_VALUE else 5,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        onOverflow = { isExpanded = it.not() },
      )
      if (!isExpanded) {
        ReadMoreOutlinedButton(
          readMore = readMore,
          onClick = { isExpanded = true },
          modifier = Modifier
            .testTag(SessionItemDetailReadMoreButtonTestTag)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp),
        )
      }
      BorderLine(modifier = Modifier.padding(top = 24.dp))
    }
  }
}

@Composable
private fun SpeakerSection(
  speakersTitle: String,
  speakers: PersistentList<Speaker>,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text(
      text = speakersTitle,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
      style = MaterialTheme.typography.titleLarge,
    )
    Spacer(modifier = Modifier.height(8.dp))
    speakers.forEach { speaker ->
      Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Image(
          painter = rememberVectorPainter(image = Icons.Default.Person),
          contentDescription = null,
          modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(
              BorderStroke(1.dp, md_theme_light_outline),
              RoundedCornerShape(12.dp),
            ),
        )
        Column(
          modifier = Modifier
            .fillMaxHeight()
            .padding(start = 24.dp),
        ) {
          Text(
            text = speaker.name,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
          )
        }
      }
    }
    BorderLine(modifier = Modifier.padding(top = 24.dp))
  }
}

@Composable
private fun ArchiveSectionIconButton(
  icon: @Composable () -> Unit,
  label: @Composable () -> Unit,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  iconSpacing: Dp = 8.dp,
) {
  Surface(
    modifier = modifier,
    color = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.onPrimary,
    shape = RoundedCornerShape(100.dp),
    onClick = { onClick() },
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(
        space = iconSpacing,
        alignment = Alignment.CenterHorizontally,
      ),
    ) {
      icon()
      label()
    }
  }
}

@Composable
private fun BorderLine(modifier: Modifier = Modifier) {
  Spacer(
    modifier = modifier
      .fillMaxWidth()
      .height(1.dp)
      .background(MaterialTheme.colorScheme.outlineVariant),
  )
}

@Composable
private fun ReadMoreOutlinedButton(
  readMore: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Surface(
    modifier = modifier.fillMaxWidth(),
    shape = RoundedCornerShape(100.dp),
    border = BorderStroke(
      width = 1.dp,
      color = MaterialTheme.colorScheme.outline,
    ),
    onClick = onClick,
  ) {
    Text(
      modifier = Modifier.padding(vertical = 10.dp),
      text = readMore,
      fontSize = 14.sp,
      textAlign = TextAlign.Center,
      color = MaterialTheme.colorScheme.primary,
    )
  }
}
