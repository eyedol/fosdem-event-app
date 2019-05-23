/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addhen.fosdem

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

class AppUtilities @Inject constructor(private val initializers: Array<AppUtility>) : AppUtility {

    override fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}

class TimberUtility : AppUtility {

    override fun init(application: Application) {
        val tree = Timber.DebugTree()
        Timber.plant(tree)
    }
}

class LeakCanaryUtility : AppUtility {

    override fun init(application: Application) {
        if (!LeakCanary.isInAnalyzerProcess(application)) {
            LeakCanary.install(application)
        }
    }
}

class StethoUtility : AppUtility {

    override fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(application)
        }
    }
}
