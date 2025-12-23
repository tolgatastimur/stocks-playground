package com.tolga.stocks_playground.data.remote.dto

data class IndexConstituentsResponse(
    val symbol: String? = null,
    val constituents: List<String> = emptyList(),
    val updated: String? = null,
    val changes: Changes? = null
) {
    data class Changes(
        val added: List<String> = emptyList(),
        val removed: List<String> = emptyList()
    )
}

