package com.addhen.fosdem.data.sqldelight.api

import app.cash.sqldelight.db.SqlDriver

interface SqlDriverFactory {

  fun createDriver(): SqlDriver
}
