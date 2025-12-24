package com.tolga.stocks_playground

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable data object PortfolioTab : NavKey
@Serializable data object ProfileTab : NavKey

// Example “inner” destinations inside a tab
@Serializable data class StockDetails(val id: String) : NavKey