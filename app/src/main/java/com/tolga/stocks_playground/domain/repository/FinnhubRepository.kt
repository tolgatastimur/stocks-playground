package com.tolga.stocks_playground.domain.repository

import com.tolga.stocks_playground.data.remote.dto.CandleResponse
import com.tolga.stocks_playground.data.remote.dto.CompanyProfileDto
import com.tolga.stocks_playground.data.remote.dto.NewsDto
import com.tolga.stocks_playground.data.remote.dto.QuoteDto
import com.tolga.stocks_playground.data.remote.dto.SymbolLookupResponse

interface FinnhubRepository {
    suspend fun getQuote(symbol: String): QuoteDto

    suspend fun getCompanyProfile(symbol: String): CompanyProfileDto

    suspend fun searchSymbols(query: String): SymbolLookupResponse

    suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CandleResponse

    suspend fun getCompanyNews(
        symbol: String,
        from: String,
        to: String
    ): List<NewsDto>
}

