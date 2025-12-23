package com.tolga.stocks_playground.data.remote.dto

data class CandleResponse(
    val c: List<Double> = emptyList(), // close prices
    val h: List<Double> = emptyList(), // high prices
    val l: List<Double> = emptyList(), // low prices
    val o: List<Double> = emptyList(), // open prices
    val s: String? = null, // status
    val t: List<Long> = emptyList(), // timestamps
    val v: List<Long> = emptyList() // volumes
)

