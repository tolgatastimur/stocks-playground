package com.tolga.stocks_playground.ui.portfolio

data class PortfolioUiState(
    val stockQuotes: List<StockQuoteUi> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
