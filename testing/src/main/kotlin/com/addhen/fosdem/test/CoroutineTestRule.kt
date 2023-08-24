package com.addhen.fosdem.test

import com.addhen.fosdem.core.api.AppCoroutineDispatchers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@ExperimentalCoroutinesApi
class CoroutineTestRule(
  val dispatcher: TestDispatcher = UnconfinedTestDispatcher(TestCoroutineScheduler()),
) : BeforeEachCallback, AfterEachCallback {

  val scope = TestScope(dispatcher)

  val testDispatcherProvider = AppCoroutineDispatchers(
    io = dispatcher,
    default = dispatcher,
    databaseRead = dispatcher,
    computation = dispatcher,
    main = dispatcher,
  )

  fun runTest(block: suspend TestScope.() -> Unit) {
    scope.runTest {
      block()
    }
  }

  override fun beforeEach(context: ExtensionContext?) {
    Dispatchers.setMain(dispatcher)
  }

  override fun afterEach(context: ExtensionContext?) {
    Dispatchers.resetMain()
  }
}
