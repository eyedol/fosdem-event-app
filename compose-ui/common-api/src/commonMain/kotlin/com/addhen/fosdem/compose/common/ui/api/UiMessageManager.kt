// Copyright 2019, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@Composable
fun SnackbarMessageEffect(
  snackbarHostState: SnackbarHostState,
  message: UiMessage?,
  actionLabel: String? = null,
  onSnackbarActionPerformed: () -> Unit = {},
  onMessageShown: (id: Long) -> Unit,
) {
  message?.let {
    LaunchedEffect(it) {
      val snackBarResult = snackbarHostState.showSnackbar(
        message = it.message,
        actionLabel = actionLabel,
      )

      onMessageShown(it.id)
      if (snackBarResult == SnackbarResult.ActionPerformed) onSnackbarActionPerformed()
    }
  }
}

data class UiMessage(
  val message: String,
  val id: Long = uuid4().mostSignificantBits,
)

fun UiMessage(
  t: Throwable,
  id: Long = uuid4().mostSignificantBits,
): UiMessage = UiMessage(
  message = t.message ?: "Error occurred: $t",
  id = id,
)

class UiMessageManager {
  private val mutex = Mutex()

  private val messageEmitter = MutableStateFlow(emptyList<UiMessage>())

  /**
   * A flow emitting the current message to display.
   */
  val message: Flow<UiMessage?> = messageEmitter.map { it.firstOrNull() }.distinctUntilChanged()

  suspend fun emitMessage(message: UiMessage) {
    mutex.withLock {
      messageEmitter.value += message
    }
  }

  suspend fun clearMessage(id: Long) {
    mutex.withLock {
      messageEmitter.value = messageEmitter.value.filterNot { it.id == id }
    }
  }
}
