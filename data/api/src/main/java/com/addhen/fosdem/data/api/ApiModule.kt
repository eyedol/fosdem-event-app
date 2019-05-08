package com.addhen.fosdem.data.api

import com.addhen.fosdem.data.api.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit.NANOSECONDS
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Singleton

@Module
object ApiModule {

    private const val DISK_CACHE_SIZE: Long = 50 * 1024 * 1024 // 50MB

    private const val HTTP_TIMEOUT = 15L

    @Singleton
    @Provides
    @JvmStatic
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor { message -> Timber.tag("OkHttp").d(message) }
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.BASIC
        }
        return loggingInterceptor
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        initOkHttpBuilder(okHttpClientBuilder, loggingInterceptor)
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideFosdemApi(fosdemApi: OkHttpFosdemApi): FosdemApi = fosdemApi

    private fun initOkHttpBuilder(okHttpClientBuilder: Builder, loggingInterceptor: HttpLoggingInterceptor) {
        okHttpClientBuilder.connectTimeout(HTTP_TIMEOUT, SECONDS)
        okHttpClientBuilder.writeTimeout(HTTP_TIMEOUT, SECONDS)
        okHttpClientBuilder.readTimeout(HTTP_TIMEOUT, SECONDS)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.connectionPool(ConnectionPool(0, 1, NANOSECONDS))
    }
}
