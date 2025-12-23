package com.tolga.stocks_playground.data.remote

import com.tolga.stocks_playground.data.remote.dto.CandleResponse
import com.tolga.stocks_playground.data.remote.dto.CompanyProfileDto
import com.tolga.stocks_playground.data.remote.dto.IndexConstituentsResponse
import com.tolga.stocks_playground.data.remote.dto.NewsDto
import com.tolga.stocks_playground.data.remote.dto.QuoteDto
import com.tolga.stocks_playground.data.remote.dto.SymbolLookupResponse
import retrofit2.http.GET
import retrofit2.http.Query

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

    @GET("index/constituents")
    suspend fun getIndexConstituents(
        @Query("symbol") symbol: String
    ): IndexConstituentsResponse
}

