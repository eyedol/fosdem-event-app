// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api

import app.cash.sqldelight.Transacter
import app.cash.sqldelight.TransactionWithoutReturn
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

suspend fun Transacter.transactionWithContext(
  coroutineContext: CoroutineContext,
  noEnclosing: Boolean = false,
  body: TransactionWithoutReturn.() -> Unit,
) {
  withContext(coroutineContext) {
    this@transactionWithContext.transaction(noEnclosing) {
      body()
    }
  }
}
