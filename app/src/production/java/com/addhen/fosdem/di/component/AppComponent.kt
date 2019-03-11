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

package com.addhen.fosdem.di.component

import com.addhen.fosdem.FosdemApp
import com.addhen.fosdem.base.di.module.ViewModelBuilder
import com.addhen.fosdem.di.module.ProductionAppModule
import com.addhen.fosdem.di.scope.ActivityScope
import com.addhen.fosdem.main.view.MainBuilder
import com.addhen.fosdem.sessions.view.SessionBottomSheetBuilder
import com.addhen.fosdem.sessions.view.SessionFilterBuilder
import com.addhen.fosdem.sessions.view.SessionsBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Initializes development flavor related dagger components.
 */
@ActivityScope
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ProductionAppModule::class,
        ViewModelBuilder::class,
        MainBuilder::class,
        SessionFilterBuilder::class,
        SessionBottomSheetBuilder::class,
        SessionsBuilder::class]
)
interface AppComponent : AndroidInjector<FosdemApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FosdemApp>()
}
