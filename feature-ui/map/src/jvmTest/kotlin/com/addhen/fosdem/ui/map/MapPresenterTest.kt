package com.addhen.fosdem.ui.map

import com.addhen.fosdem.compose.common.ui.api.ImageVectorResource
import com.addhen.fosdem.core.api.screens.MapScreen
import com.addhen.fosdem.test.CoroutineTestRule
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import kotlin.test.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension


@OptIn(ExperimentalCoroutinesApi::class)
internal class MapPresenterTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()

  private val navigator = FakeNavigator(MapScreen)

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
