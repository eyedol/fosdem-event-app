// Copyright 2019, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.compose.common.ui.api

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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