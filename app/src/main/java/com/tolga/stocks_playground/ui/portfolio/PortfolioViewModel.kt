package com.tolga.stocks_playground.ui.portfolio

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tolga.stocks_playground.domain.model.MockUserProfile
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

    private val _uiState = MutableStateFlow(PortfolioUiState())
    val uiState: StateFlow<PortfolioUiState> = _uiState.asStateFlow()

    init {
        loadQuotesForSymbols()
    }

    fun loadQuotesForSymbols(
        symbols: List<String> = listOf("AAPL", "TSLA", "MSFT", "GOOGL", "NVDA")
    ) {
        _uiState.value = _uiState.value.copy(isLoading = true, error = null)
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
                Log.d(
                    "PortfolioViewModel",
                    "Loaded quotes for symbols: ${quotes.keys.joinToString()}, mapped to ${stockQuotes.size} stock quotes"
                )

                Log.d(
                    "PortfolioViewModel",
                    "User holdings: ${userProfileSession.userProfile.holdings}"
                )
                _uiState.value = _uiState.value.copy(
                    stockQuotes = stockQuotes,
                    isLoading = false,
                    error = null
                )
            }.onFailure { throwable ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = throwable.message ?: "Failed to load quotes"
                )
            }
        }
    }
}