package com.tolga.stocks_playground.data.remote.dto

data class NewsDto(
    val category: String? = null,
    val datetime: Long? = null,
    val headline: String? = null,
    val id: Long? = null,
    val image: String? = null,
    val related: String? = null,
    val source: String? = null,
    val summary: String? = null,
    val url: String? = null
)

