// Copyright 2023, Addhen Limited and the FOSDEM Event app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.data.sqldelight.api

import app.cash.sqldelight.db.SqlDriver

interface SqlDriverFactory {

  fun createDriver(): SqlDriver
}
