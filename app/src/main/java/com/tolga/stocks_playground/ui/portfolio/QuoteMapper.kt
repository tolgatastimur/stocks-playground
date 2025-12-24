package com.tolga.stocks_playground.ui.portfolio

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.ui.graphics.Color
import com.tolga.stocks_playground.data.remote.dto.QuoteDto

object QuoteMapper {
    private val GreenColor = Color(0xFF4CAF50) // Green for positive
    private val RedColor = Color(0xFFF44336) // Red for negative

    fun toStockQuoteUi(symbol: String, quoteDto: QuoteDto): StockQuoteUi {
        val currentPrice = quoteDto.c ?: 0.0
        val previousClose = quoteDto.pc ?: 0.0
        val changeAmount = currentPrice - previousClose
        val changePercent = if (previousClose != 0.0) {
            (changeAmount / previousClose) * 100
        } else {
            0.0
        }

        val isPositive = changeAmount >= 0
        val changeColor = if (isPositive) GreenColor else RedColor
        val changeIcon = if (isPositive) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward

        return StockQuoteUi(
            stockName = symbol,
            currentPrice = "$".plus(currentPrice),
            changeAmount = changeAmount,
            changePercent = changePercent,
            changeColor = changeColor,
            changeIcon = changeIcon
        )
    }

    fun toStockQuoteUiList(quotes: Map<String, QuoteDto>): List<StockQuoteUi> {
        return quotes.map { (symbol, quoteDto) ->
            toStockQuoteUi(symbol, quoteDto)
        }
    }
}

