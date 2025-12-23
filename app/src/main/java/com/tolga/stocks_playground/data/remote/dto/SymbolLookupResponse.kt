package com.tolga.stocks_playground.data.remote.dto

data class SymbolLookupResponse(
    val count: Int? = null,
    val result: List<SymbolDto> = emptyList()
)

