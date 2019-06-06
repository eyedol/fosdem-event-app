package com.addhen.fosdem.sessions.model

sealed class ScreenTab {

    abstract val title: String

    abstract val index: Int

    open class Tab(override val title: String, override val index: Int) : ScreenTab()

    object Saturday : Tab("Saturday", 1)
    object Sunday : Tab("Sunday", 2)

    companion object {
        val session = listOf(Saturday, Sunday)
    }
}
