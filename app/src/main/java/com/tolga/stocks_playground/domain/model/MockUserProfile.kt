package com.tolga.stocks_playground.domain.model

object MockUserProfile {
    fun create(): UserProfile {
        return UserProfile(
            name = "Mock User",
            holdings = emptyList() // Start with empty holdings to show initial state
            // Uncomment below to test with data:
            /*holdings = listOf(
                StockHolding(symbol = "AAPL", quantity = 12, averagePurchasePrice = 270.00),
                StockHolding(symbol = "TSLA", quantity = 8, averagePurchasePrice = 450.00),
                StockHolding(symbol = "MSFT", quantity = 15, averagePurchasePrice = 400.00),
                StockHolding(symbol = "GOOGL", quantity = 3, averagePurchasePrice = 300.00),
                StockHolding(symbol = "NVDA", quantity = 5, averagePurchasePrice = 90.00)
            )*/
        )
    }
}

