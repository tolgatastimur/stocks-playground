package com.tolga.stocks_playground.ui.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PortfolioScreen(viewModel: PortfolioViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            Row(modifier = Modifier.padding(16.dp)) {
                Text("My Stocks")
                Spacer(modifier = Modifier.weight(1f))
                Text("View All")
            }

            Column(modifier = Modifier.padding(16.dp)) {
                uiState.stockQuotes.forEach { item ->
                    StocksCard(quote = item)
                }
            }
        }
    }
}

@Composable
fun StocksCard(quote: StockQuoteUi) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp)) {
            CircleTextAvatar(quote.stockName)
            Spacer(Modifier.width(8.dp))
            Text(modifier = Modifier.padding(start = 8.dp, top = 8.dp), text = quote.stockName)
            Spacer(Modifier.weight(1f))
            Column {
                Text(
                    text = quote.currentPrice,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        modifier = Modifier.size(12.dp),
                        imageVector = quote.changeIcon,
                        contentDescription = null,
                        tint = quote.changeColor
                    )
                    Text(
                        text = String.format(
                            "%.2f%%",
                            quote.changePercent
                        ),
                        fontSize = 12.sp,
                        color = quote.changeColor,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CircleTextAvatar(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.size(60.dp).clip(CircleShape).background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleMedium
        )
    }

}