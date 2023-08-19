package com.addhen.fosdem.core.api

import com.slack.circuit.runtime.Screen

abstract class AppScreen(val name: String) : Screen {
  open val arguments: Map<String, *>? = null
}
