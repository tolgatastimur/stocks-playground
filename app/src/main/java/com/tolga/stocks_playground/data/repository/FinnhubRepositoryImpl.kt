package com.tolga.stocks_playground.data.repository

import com.tolga.stocks_playground.data.remote.FinnhubApi
import com.tolga.stocks_playground.data.remote.dto.CandleResponse
import com.tolga.stocks_playground.data.remote.dto.CompanyProfileDto
import com.tolga.stocks_playground.data.remote.dto.IndexConstituentsResponse
import com.tolga.stocks_playground.data.remote.dto.NewsDto
import com.tolga.stocks_playground.data.remote.dto.QuoteDto
import com.tolga.stocks_playground.data.remote.dto.SymbolLookupResponse
import com.tolga.stocks_playground.domain.repository.FinnhubRepository
import javax.inject.Inject

class FinnhubRepositoryImpl @Inject constructor(
    private val api: FinnhubApi
) : FinnhubRepository {
    override suspend fun getQuote(symbol: String): QuoteDto = api.getQuote(symbol)

    override suspend fun getCompanyProfile(symbol: String): CompanyProfileDto =
        api.getCompanyProfile(symbol)

    override suspend fun searchSymbols(query: String): SymbolLookupResponse =
        api.searchSymbols(query)

    override suspend fun getCandles(
        symbol: String,
        resolution: String,
        from: Long,
        to: Long
    ): CandleResponse = api.getCandles(symbol, resolution, from, to)

    override suspend fun getCompanyNews(
        symbol: String,
        from: String,
        to: String
    ): List<NewsDto> = api.getCompanyNews(symbol, from, to)

    override suspend fun getIndexConstituents(symbol: String): IndexConstituentsResponse =
        api.getIndexConstituents(symbol)
}

