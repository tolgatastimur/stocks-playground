package com.tolga.stocks_playground.domain.model

data class UserProfile(
    val name: String = "Mock User",
    val holdings: List<StockHolding> = emptyList()
) {
    val totalShares: Int
        get() = holdings.sumOf { it.quantity }
}

