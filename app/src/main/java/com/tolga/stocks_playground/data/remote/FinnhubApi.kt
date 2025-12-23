package com.tolga.stocks_playground.data.remote

import com.tolga.stocks_playground.BuildConfig
import com.tolga.stocks_playground.data.remote.dto.CandleResponse
import com.tolga.stocks_playground.data.remote.dto.CompanyProfileDto
import com.tolga.stocks_playground.data.remote.dto.NewsDto
import com.tolga.stocks_playground.data.remote.dto.QuoteDto
import com.tolga.stocks_playground.data.remote.dto.SymbolLookupResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

interface FinnhubApi {
    @GET("quote")
    suspend fun getQuote(
        @Query("symbol") symbol: String
    ): QuoteDto

    @GET("stock/profile2")
    suspend fun getCompanyProfile(
        @Query("symbol") symbol: String
    ): CompanyProfileDto

    @GET("search")
    suspend fun searchSymbols(
        @Query("q") query: String
    ): SymbolLookupResponse

    @GET("stock/candle")
    suspend fun getCandles(
        @Query("symbol") symbol: String,
        @Query("resolution") resolution: String,
        @Query("from") from: Long,
        @Query("to") to: Long
    ): CandleResponse

    @GET("company-news")
    suspend fun getCompanyNews(
        @Query("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): List<NewsDto>

    companion object {
        private const val BASE_URL = "https://finnhub.io/api/v1/"

        fun create(
            apiKey: String = BuildConfig.FINNHUB_API_KEY,
            enableLogging: Boolean = true
        ): FinnhubApi {
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

            if (enableLogging) {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
                clientBuilder.addInterceptor(logging)
            }

            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()

            return retrofit.create(FinnhubApi::class.java)
        }
    }
}

