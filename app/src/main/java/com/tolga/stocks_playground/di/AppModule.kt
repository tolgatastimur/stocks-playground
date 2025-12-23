package com.tolga.stocks_playground.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tolga.stocks_playground.BuildConfig
import com.tolga.stocks_playground.data.remote.FinnhubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideFinnhubApi(moshi: Moshi, okHttpClient: OkHttpClient): FinnhubApi {
        val BASE_URL = "https://finnhub.io/api/v1/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(FinnhubApi::class.java)
    }

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val apiKey = BuildConfig.FINNHUB_API_KEY
        val authInterceptor = Interceptor { chain ->
            val original = chain.request()
            val updatedUrl = original.url.newBuilder()
                .addQueryParameter("token", apiKey)
                .build()
            val updated = original.newBuilder()
                .url(updatedUrl)
                .build()
            chain.proceed(updated)
        }

        val clientBuilder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            clientBuilder.addInterceptor(logging)
        }

        return clientBuilder.build()
    }
}