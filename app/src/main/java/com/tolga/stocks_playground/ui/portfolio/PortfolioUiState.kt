package com.tolga.stocks_playground.ui.portfolio

import com.tolga.stocks_playground.data.remote.dto.QuoteDto

data class PortfolioUiState(
    val quotes: Map<String, QuoteDto> = emptyMap(),
    val isLoading: Boolean = false,
    val error: String? = null
)
