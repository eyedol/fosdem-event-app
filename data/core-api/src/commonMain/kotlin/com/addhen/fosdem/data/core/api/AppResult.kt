// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api

sealed class AppResult<out T> {
  data class Success<out T>(val data: T) : AppResult<T>()
  data class Error(val appError: AppError) : AppResult<Nothing>()
}

inline fun <reified T> AppResult<T>.onSuccess(action: (T) -> Unit): AppResult<T> {
  if (this is AppResult.Success) {
    action(data)
  }
  return this
}

inline fun <reified T> AppResult<T>.onError(action: (AppError) -> Unit): AppResult<T> {
  if (this is AppResult.Error) {
    action(appError)
  }
  return this
}
