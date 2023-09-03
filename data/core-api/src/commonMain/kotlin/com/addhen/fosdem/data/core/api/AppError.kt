// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.core.api

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.util.cio.ChannelReadException
import kotlinx.coroutines.TimeoutCancellationException

fun Throwable.toAppError(): AppError {
  return when (this) {
    is AppError -> this
    is ResponseException ->
      return AppError.ApiException.ServerException(this)

    is ChannelReadException ->
      return AppError.ApiException.NetworkException(this)

    is TimeoutCancellationException, is SocketTimeoutException -> {
      AppError.ApiException.TimeoutException(this)
    }

    else -> AppError.UnknownException(this)
  }
}

sealed class AppError(cause: Throwable?) : RuntimeException(cause) {

  sealed class ApiException(cause: Throwable?) : AppError(cause) {
    class NetworkException(cause: Throwable?) : ApiException(cause)
    class ServerException(cause: Throwable?) : ApiException(cause)
    class TimeoutException(cause: Throwable?) : ApiException(cause)
  }

  class UnknownException(cause: Throwable?) : AppError(cause)
}
