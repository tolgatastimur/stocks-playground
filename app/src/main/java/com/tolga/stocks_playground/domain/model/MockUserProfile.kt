package com.tolga.stocks_playground.domain.model

object MockUserProfile {
    fun create(): UserProfile {
        return UserProfile(
            name = "Mock User",
            holdings = listOf(
                StockHolding(symbol = "AAPL", quantity = 12),
                StockHolding(symbol = "TSLA", quantity = 8),
                StockHolding(symbol = "MSFT", quantity = 15),
                StockHolding(symbol = "GOOGL", quantity = 3),
                StockHolding(symbol = "NVDA", quantity = 5)
            )
        )
    }
}

