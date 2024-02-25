// Copyright 2024, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api

import kotlinx.coroutines.CancellationException

inline fun Result<*>.onException(
  block: (Throwable) -> Unit,
) {
  val e = exceptionOrNull()
  when {
    e is CancellationException -> throw e
    e != null -> block(e)
  }
}
