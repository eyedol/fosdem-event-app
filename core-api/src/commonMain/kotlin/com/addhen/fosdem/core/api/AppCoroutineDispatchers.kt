package com.addhen.fosdem.core.api

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatchers(
  val io: CoroutineDispatcher,
  val default: CoroutineDispatcher,
  val databaseRead: CoroutineDispatcher,
  val computation: CoroutineDispatcher,
  val main: CoroutineDispatcher
)
