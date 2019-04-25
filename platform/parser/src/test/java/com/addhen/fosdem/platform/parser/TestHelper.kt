package com.addhen.fosdem.platform.parser

import java.io.InputStream

object TestHelper {

    @Throws(Exception::class)
    @JvmStatic
    fun getStringFromFile(filePath: String): InputStream {
        return javaClass.classLoader
            .getResource(filePath)
            .openStream()
    }
}
