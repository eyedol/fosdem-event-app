// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.session.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.compose.common.ui.api.LocalConferenceInfo
import com.addhen.fosdem.compose.common.ui.api.Res
import com.addhen.fosdem.compose.common.ui.api.imageVectorResource
import com.addhen.fosdem.compose.common.ui.api.tagline_beer
import com.addhen.fosdem.compose.common.ui.api.tagline_dev_rooms
import com.addhen.fosdem.compose.common.ui.api.tagline_free_software
import com.addhen.fosdem.compose.common.ui.api.tagline_hackers
import com.addhen.fosdem.compose.common.ui.api.tagline_lightning_talks
import com.addhen.fosdem.compose.common.ui.api.tagline_open_source
import com.addhen.fosdem.compose.common.ui.api.tagline_talks
import com.addhen.fosdem.compose.common.ui.api.theme.logoColors
import com.addhen.fosdem.compose.common.ui.api.theme.tagColors
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

@Immutable
data class Tag(val title: String, val color: Color)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SessionHeader(modifier: Modifier = Modifier) {
  val conferenceInfo = LocalConferenceInfo.current
  val appLogo = imageVectorResource(ImageVectorResource.FosdemLogo)
  val tags = tags()

  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
  ) {
    Column(Modifier.weight(2f)) {
      Text(
        text = buildAnnotatedString {
          withStyle(style = MaterialTheme.typography.displaySmall.toSpanStyle()) {
            append(conferenceInfo.name)
          }
          withStyle(
            style = MaterialTheme.typography.displaySmall
              .toSpanStyle()
              .copy(color = MaterialTheme.colorScheme.primary),
          ) {
            append("\u02BC${conferenceInfo.shortYear}")
          }
        },
      )
      Spacer(modifier = Modifier.height(1.dp))
      Text(
        text = conferenceInfo.location,
        style = MaterialTheme.typography.labelMedium,
      )
      FlowRow(
        Modifier
          .wrapContentWidth()
          .wrapContentHeight(align = Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.Center,
        maxItemsInEachRow = 3,

      ) {
        for (tag in tags) {
          TagItem(tag)
        }
      }
    }

    Box(
      modifier = Modifier
        .weight(1f)
        .padding(8.dp)
        .wrapContentWidth(),
    ) {
      Box(
        modifier = Modifier.size(
          width = 185.dp,
          height = 169.dp,
        ),
      ) {
        Image(
          modifier = Modifier.size(
            width = 120.dp,
            height = 120.dp,
          ),
          imageVector = appLogo,
          colorFilter = ColorFilter.tint(logoColors().background),
          contentDescription = null,
        )
      }
    }
  }
}

@Composable
private fun TagItem(tag: Tag) {
  Text(
    text = tag.title,
    color = tag.color,
    fontSize = 16.sp,
    textAlign = TextAlign.Center,
    modifier = Modifier
      .padding(2.dp)
      .wrapContentHeight(),
  )
}

@Composable
private fun tags(): PersistentList<Tag> {
  return listOf(
    Tag(stringResource(Res.string.tagline_beer), tagColors().tagColorMain),
    Tag(stringResource(Res.string.tagline_open_source), tagColors().tagColorAlt),
    Tag(stringResource(Res.string.tagline_free_software), tagColors().tagColorMain),
    Tag(stringResource(Res.string.tagline_lightning_talks), tagColors().tagColorAlt),
    Tag(stringResource(Res.string.tagline_dev_rooms), tagColors().tagColorMain),
    Tag(stringResource(Res.string.tagline_talks), tagColors().tagColorAlt),
    Tag(stringResource(Res.string.tagline_hackers), tagColors().tagColorMain),
  ).toPersistentList()
}
