package com.tolga.stocks_playground.data.remote.dto

data class CompanyProfileDto(
    val country: String? = null,
    val currency: String? = null,
    val exchange: String? = null,
    val ipo: String? = null,
    val marketCapitalization: Double? = null,
    val name: String? = null,
    val phone: String? = null,
    val shareOutstanding: Double? = null,
    val ticker: String? = null,
    val weburl: String? = null,
    val finnhubIndustry: String? = null,
    val logo: String? = null
)

