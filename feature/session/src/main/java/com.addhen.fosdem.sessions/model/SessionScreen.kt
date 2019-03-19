package com.addhen.fosdem.sessions.model

sealed class SessionScreen {

    abstract val title: String

    abstract val tag: String

    open class Tab(override val title: String, override val tag: String) : SessionScreen()

    object Saturday : Tab("Saturday", "sat")
    object Sunday : Tab("Sunday", "sun")
    object Bookmark : Tab("Bookmark", "bookmark")

    companion object {
        val tabs = listOf(Saturday, Sunday, Bookmark)
    }
}
