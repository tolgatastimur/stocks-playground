package com.tolga.stocks_playground.ui.portfolio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class StockQuoteUi(
    val stockName: String,
    val currentPrice: String,
    val changeAmount: Double, // positive for increase, negative for decrease
    val changePercent: Double, // percentage change
    val changeColor: Color, // green for positive, red for negative
    val changeIcon: ImageVector // up arrow for positive, down arrow for negative
)

