// Copyright 2022, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.core.api

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
      AppError.ApiException
        .TimeoutException(this)
    }

    else -> AppError.UnknownException(this)
  }
}

sealed class AppError : RuntimeException {
  constructor()
  constructor(message: String?) : super(message)
  constructor(message: String?, cause: Throwable?) : super(message, cause)
  constructor(cause: Throwable?) : super(cause)

  sealed class ApiException(cause: Throwable?) : AppError(cause) {
    class NetworkException(cause: Throwable?) : ApiException(cause)
    class ServerException(cause: Throwable?) : ApiException(cause)
    class TimeoutException(cause: Throwable?) : ApiException(cause)
    class SessionNotFoundException(cause: Throwable?) : AppError(cause)
    class UnknownException(cause: Throwable?) : AppError(cause)
  }

  sealed class ExternalIntegrationError(cause: Throwable?) : AppError(cause) {
    class NoCalendarIntegrationFoundException(cause: Throwable?) :
      ExternalIntegrationError(cause)
  }

  class UnknownException(cause: Throwable?) : AppError(cause)
}
