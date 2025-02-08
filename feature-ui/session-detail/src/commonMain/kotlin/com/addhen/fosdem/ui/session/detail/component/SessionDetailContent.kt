// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
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
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.ClickableLinkText
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.attachment_title
import com.addhen.fosdem.compose.common.ui.api.link_title
import com.addhen.fosdem.compose.common.ui.api.read_more_label
import com.addhen.fosdem.compose.common.ui.api.speaker_title
import com.addhen.fosdem.compose.common.ui.api.theme.iconColors
import com.addhen.fosdem.compose.common.ui.api.theme.md_theme_light_outline
import com.addhen.fosdem.model.api.Speaker
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

const val SessionItemDetailReadMoreButtonTestTag = "SessionItemDetailReadMoreButtonTestTag"

@Immutable
data class ClickableTextData(val name: String, val url: String)

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
    descriptionText.isBlank().not() -> descriptionText
    else -> abstractText
  }
  Column(modifier = modifier) {
    DescriptionSection(
      readMore = stringResource(Res.string.read_more_label),
      description = description,
      onLinkClick = onLinkClick,
    )

    if (uiState.event.speakers.isNotEmpty()) {
      SpeakerSection(
        stringResource(Res.string.speaker_title),
        uiState.event.speakers.toPersistentList(),
      )
    }

    if (uiState.event.links.isNotEmpty()) {
      MaterialSection(
        stringResource(Res.string.link_title),
        uiState.event.links.map { ClickableTextData(it.text, it.url) }.toPersistentList(),
        onLinkClick = onLinkClick,
      )
    }

    if (uiState.event.attachments.isNotEmpty()) {
      MaterialSection(
        stringResource(Res.string.attachment_title),
        uiState.event.attachments.map { ClickableTextData(it.name, it.url) }.toPersistentList(),
        onLinkClick = onLinkClick,
      )
    }
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
    Column(
      modifier = Modifier
        .animateContentSize()
        .then(modifier),
    ) {
      ClickableLinkText(
        style = MaterialTheme
          .typography
          .bodyLarge
          .copy(color = MaterialTheme.colorScheme.onSurface),
        content = description,
        isHtmlContent = true,
        onLinkClick = onLinkClick,
        regex = "(?:https?|ftp)://[\\w+&@#/%?=~_|!:,.;-]*[\\w+&@#/%=~_|]".toRegex(),
        overflow = TextOverflow.Ellipsis,
        maxLines = if (isExpanded) Int.MAX_VALUE else 5,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp),
        onContentLick = { isExpanded = false },
        onOverflow = { isExpanded = it.not() },
      )
      if (isExpanded.not()) {
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
    for (speaker in speakers) {
      Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Image(
          painter = rememberVectorPainter(image = Icons.Default.Person),
          contentDescription = null,
          colorFilter = ColorFilter.tint(iconColors().background),
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
private fun MaterialSection(
  title: String,
  materials: PersistentList<ClickableTextData>,
  onLinkClick: (url: String) -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    Text(
      text = title,
      modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp),
      style = MaterialTheme.typography.titleLarge,
    )
    Column(
      modifier = Modifier
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        .fillMaxWidth()
        .fillMaxWidth(),
      verticalArrangement = Arrangement.spacedBy(space = 8.dp),
    ) {
      for (material in materials) {
        val nameRegex = material.name
          .replace("(", "\\(")
          .replace(")", "\\)")
          .replace("/", "\\/")
          .toRegex()

        ClickableLinkText(
          style = MaterialTheme.typography.bodyMedium,
          content = " \u2022 ${material.name}",
          onLinkClick = onLinkClick,
          regex = nameRegex,
          url = material.url,
        )
      }
    }
    Spacer(modifier = Modifier.height(24.dp))
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
