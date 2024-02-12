// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.events.repository

import com.addhen.fosdem.test.CoroutineTestRule
import org.junit.jupiter.api.extension.RegisterExtension

class LicensesDataRepositoryTest {

  @JvmField
  @RegisterExtension
  val coroutineTestRule = CoroutineTestRule()
}
