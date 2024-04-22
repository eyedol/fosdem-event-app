// Copyright 2024, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.ui.map

import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.test.CoroutineTestRule
import com.slack.circuit.test.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
internal class MapPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val sut = MapPresenter()

  @Test
  fun `should show map`() = coroutineTestRule.runTest {
    val expectedMapUiState = MapUiState(imageResource = ImageVectorResource.FosdemCampusMap)
    sut.test {
      val actualMapUiState = awaitItem()

      assertEquals(expectedMapUiState, actualMapUiState)
    }
  }
}
