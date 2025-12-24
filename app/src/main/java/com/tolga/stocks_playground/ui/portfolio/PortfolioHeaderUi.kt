package com.tolga.stocks_playground.ui.portfolio

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class PortfolioHeaderUi(
    val totalValue: String,
    val totalChangeAmount: Double,
    val totalChangePercent: Double,
    val changeColor: Color,
    val changeIcon: ImageVector
)

