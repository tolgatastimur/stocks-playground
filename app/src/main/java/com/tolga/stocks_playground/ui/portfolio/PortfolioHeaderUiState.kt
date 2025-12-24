package com.tolga.stocks_playground.ui.portfolio

import java.text.NumberFormat
import java.util.Locale

data class PortfolioHeaderUiState(
    val totalPortfolioValue: Double = 0.0,
    val totalGainLoss: Double = 0.0,
    val totalGainLossPercent: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null
) {
    val friendlyTotalValue: String
        get() {
            val formatter = NumberFormat.getCurrencyInstance(Locale.US)
            formatter.maximumFractionDigits = 2
            formatter.minimumFractionDigits = 2
            return formatter.format(totalPortfolioValue)
        }
    
    val friendlyGainLoss: String
        get() {
            val formatter = NumberFormat.getCurrencyInstance(Locale.US)
            formatter.maximumFractionDigits = 2
            formatter.minimumFractionDigits = 2
            return formatter.format(totalGainLoss)
        }
    
    val friendlyGainLossPercent: String
        get() {
            val sign = if (totalGainLossPercent >= 0) "+" else ""
            return "$sign${String.format("%.2f", totalGainLossPercent)}%"
        }
}
