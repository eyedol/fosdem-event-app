// Copyright 2023, Addhen Limited and the FOSDEM app project contributors
// SPDX-License-Identifier: Apache-2.0

package com.addhen.fosdem.android.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class MainActivity : ComponentActivity() {

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)
    handleIntent(intent)
  }

  override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    handleIntent(intent)
  }

  open fun handleIntent(intent: Intent) {}

  override fun finishAfterTransition() {
    val resultData = Intent()
    val result = onPopulateResultIntent(resultData)
    setResult(result, resultData)

    super.finishAfterTransition()
  }

  open fun onPopulateResultIntent(intent: Intent): Int = Activity.RESULT_OK
}
