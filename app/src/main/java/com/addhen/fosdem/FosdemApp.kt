package com.addhen.fosdem

import com.addhen.fosdem.di.component.DaggerAppComponent
import dagger.android.support.DaggerApplication
import javax.inject.Inject

open class FosdemApp : DaggerApplication() {

    @Inject
    lateinit var appUtilities: AppUtilities

    override fun onCreate() {
        super.onCreate()
        appUtilities.init(this)
    }

    override fun applicationInjector() = DaggerAppComponent.builder().create(this)
}
