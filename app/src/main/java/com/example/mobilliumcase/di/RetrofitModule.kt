package com.example.mobilliumcase.di

import com.example.mobilliumcase.data.MobilliumApi
import com.example.mobilliumcase.util.Constants.API_KEY
import com.example.mobilliumcase.util.Constants.BASE_URL
import com.example.mobilliumcase.util.Constants.LANGUAGE
import com.example.mobilliumcase.util.Constants.connectionTimeout
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    private val contentType = "application/json".toMediaType()

    private val jsonConverterFactory = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key",API_KEY)
                    .addQueryParameter("language", LANGUAGE)
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                chain.proceed(requestBuilder.build())
            }
            .addInterceptor(HttpLoggingInterceptor().also {
                it.level = setLogLevel()
            })
            .followRedirects(true)
            .readTimeout(timeout = 60, TimeUnit.SECONDS)
            .connectTimeout(timeout = 60, TimeUnit.SECONDS)
            .writeTimeout(timeout = 60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun setLogLevel(): HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.HEADERS


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(jsonConverterFactory.asConverterFactory(contentType))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): MobilliumApi {
        return retrofit.create(MobilliumApi::class.java)
    }
}