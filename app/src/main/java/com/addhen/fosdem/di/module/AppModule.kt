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

package com.addhen.fosdem.di.module

import com.addhen.fosdem.AppUtilities
import com.addhen.fosdem.FosdemApp
import com.addhen.fosdem.TimberUtility
import com.addhen.fosdem.base.CoroutineDispatchers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module generally available for the entire lifecycle of the app.
 */

@Module
internal object AppModule {

    @Singleton
    @Provides
    @JvmStatic
    fun provideAppContext(app: FosdemApp) = app.applicationContext

    @Provides
    @JvmStatic
    fun provideAppUtilities(
        timberUtility: TimberUtility
    ): AppUtilities = AppUtilities(timberUtility)

    @Provides
    @JvmStatic
    fun provideCoroutineDispatchers() = CoroutineDispatchers()

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttp() = {

    }
}
