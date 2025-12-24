package com.tolga.stocks_playground.ui.portfolio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolga.stocks_playground.domain.model.UserProfileSession
import com.tolga.stocks_playground.domain.repository.FinnhubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.async
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val finnhubRepository: FinnhubRepository,
    private val userProfileSession: UserProfileSession
): ViewModel() {

    private val _uiStateStocks = MutableStateFlow(PortfolioUiState())
    val uiStateStocks: StateFlow<PortfolioUiState> = _uiStateStocks.asStateFlow()

    private val _uiStateHeader = MutableStateFlow(PortfolioHeaderUiState())
    val uiStateHeader: StateFlow<PortfolioHeaderUiState> = _uiStateHeader.asStateFlow()

    init {
        loadQuotesForSymbols()
    }

    fun loadQuotesForSymbols(
        symbols: List<String> = listOf("AAPL", "TSLA", "MSFT", "GOOGL", "NVDA")
    ) {
        _uiStateStocks.value = _uiStateStocks.value.copy(isLoading = true, error = null)
        viewModelScope.launch {
            val result = runCatching {
                supervisorScope {
                    symbols.associateWith { symbol ->
                        async { finnhubRepository.getQuote(symbol) }
                    }.mapValues { (_, deferred) -> deferred.await() }
                }
            }

            result.onSuccess { quotes ->
                val stockQuotes = QuoteMapper.toStockQuoteUiList(quotes)
                loadUserProfile(stockQuotes)
                _uiStateStocks.value = _uiStateStocks.value.copy(
                    stockQuotes = stockQuotes,
                    isLoading = false,
                    error = null
                )
            }.onFailure { throwable ->
                _uiStateStocks.value = _uiStateStocks.value.copy(
                    isLoading = false,
                    error = throwable.message ?: "Failed to load quotes"
                )
            }
        }
    }

    fun loadUserProfile(stockList: List<StockQuoteUi>) {
        viewModelScope.launch {
            try {
                val userProfile = userProfileSession.userProfile
                val stockMap = stockList.associateBy { it.stockName }
                
                var totalPortfolioValue = 0.0
                var totalPurchaseValue = 0.0
                
                userProfile.holdings.forEach { holding ->
                    val stockQuote = stockMap[holding.symbol]
                    if (stockQuote != null) {
                        // Parse current price from string (remove "$" and convert to double)
                        val currentPrice = stockQuote.currentPrice
                            .removePrefix("$")
                            .replace(",", "")
                            .toDoubleOrNull() ?: 0.0
                        
                        // Calculate current value of this holding
                        val currentValue = currentPrice * holding.quantity
                        totalPortfolioValue += currentValue
                        
                        // Calculate purchase value
                        val purchaseValue = holding.averagePurchasePrice * holding.quantity
                        totalPurchaseValue += purchaseValue
                    }
                }
                
                // Calculate total gain/loss
                val totalGainLoss = totalPortfolioValue - totalPurchaseValue
                val totalGainLossPercent = if (totalPurchaseValue > 0) {
                    (totalGainLoss / totalPurchaseValue) * 100
                } else {
                    0.0
                }
                
                _uiStateHeader.value = _uiStateHeader.value.copy(
                    totalPortfolioValue = totalPortfolioValue,
                    totalGainLoss = totalGainLoss,
                    totalGainLossPercent = totalGainLossPercent,
                    isLoading = false,
                    error = null
                )
                
                Log.d(
                    "PortfolioViewModel",
                    "Portfolio Value: $$totalPortfolioValue, Gain/Loss: $$totalGainLoss (${String.format("%.2f", totalGainLossPercent)}%)"
                )
            } catch (e: Exception) {
                Log.e("PortfolioViewModel", "Failed to load user profile", e)
                _uiStateHeader.value = _uiStateHeader.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to calculate portfolio"
                )
            }
        }
    }
}