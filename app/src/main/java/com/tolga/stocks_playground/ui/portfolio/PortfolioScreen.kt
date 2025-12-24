package com.tolga.stocks_playground.ui.portfolio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDownward
import androidx.compose.material.icons.outlined.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tolga.stocks_playground.ui.theme.BlueBackground
import com.tolga.stocks_playground.ui.theme.GreenBackground

@Composable
fun PortfolioScreen(viewModel: PortfolioViewModel = hiltViewModel()) {
    val uiState by viewModel.uiStateStocks.collectAsState()

    Scaffold { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(top = innerPadding.calculateTopPadding())) {
            StocksHeader(viewModel)
            Row(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "My Stocks",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "View All", color = BlueBackground, style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            LazyColumn {
                items(uiState.stockQuotes) { item ->
                    StocksCard(quote = item)
                }
            }
        }
    }
}

@Composable
fun StocksHeader(viewModel: PortfolioViewModel) {
    val uiState by viewModel.uiStateHeader.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Portfolio",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(BlueBackground)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Total Portfolio Value",
                color = Color.White,
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = uiState.friendlyTotalValue,
                color = Color.White,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PricePill(
                    uiState.friendlyGainLoss,
                    modifier = Modifier.padding(top = 6.dp),
                    isUp = uiState.totalGainLoss >= 0
                )
                Text(
                    modifier = Modifier.padding(top = 8.dp),
                    text = uiState.friendlyGainLossPercent,
                    color = GreenBackground,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
fun PricePill(
    text: String,
    isUp: Boolean,
    modifier: Modifier = Modifier
) {
    val contentColor = GreenBackground
    val pillColor = GreenBackground.copy(alpha = 0.20f)
    val icon = if (isUp) Icons.Outlined.ArrowUpward else Icons.Outlined.ArrowDownward

    Row(
        modifier = modifier
            .height(70.dp)
            .padding(end = 8.dp, top = 12.dp, bottom = 12.dp)
            .clip(RoundedCornerShape(100.dp))    // capsule ends
            .background(pillColor)
            .padding(horizontal = 16.dp),        // inner padding
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.width(4.dp))

        Text(
            text = text,
            color = contentColor,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
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
            Text(
                modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                text = quote.stockName,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
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
                    Icon(
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