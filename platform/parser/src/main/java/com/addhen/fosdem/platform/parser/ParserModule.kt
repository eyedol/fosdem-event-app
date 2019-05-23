package com.addhen.fosdem.platform.parser

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ParserModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideScheduleXmlParser(): Parser<Schedule> = ScheduleXmlParser()
}
