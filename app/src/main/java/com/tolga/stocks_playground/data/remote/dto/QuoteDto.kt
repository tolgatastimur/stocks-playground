package com.tolga.stocks_playground.data.remote.dto

data class QuoteDto(
    val c: Double? = null, // current price
    val h: Double? = null, // high price of the day
    val l: Double? = null, // low price of the day
    val o: Double? = null, // open price of the day
    val pc: Double? = null, // previous close price
    val t: Long? = null // timestamp
)

