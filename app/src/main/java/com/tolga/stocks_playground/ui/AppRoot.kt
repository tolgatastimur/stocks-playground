package com.tolga.stocks_playground.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBalance
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.tolga.stocks_playground.PortfolioTab
import com.tolga.stocks_playground.ProfileTab
import com.tolga.stocks_playground.ui.portfolio.PortfolioScreen

enum class HomeTabType { Portfolio, Settings }

@Composable
fun AppRootNavigation() {
    val portfolioBackStack = rememberNavBackStack(PortfolioTab)
    val profileBackStack = rememberNavBackStack(ProfileTab)

    var selectedTab by rememberSaveable { mutableStateOf(HomeTabType.Portfolio) }

    val activeBackStack = when (selectedTab) {
        HomeTabType.Portfolio -> portfolioBackStack
        HomeTabType.Settings -> profileBackStack
    }

    Scaffold(
        bottomBar = {
            BottomBarNav3(
                selected = selectedTab,
                onSelect = { selectedTab = it },
                onReselect = {
                    // common behavior: pop to root of that tab
                    val stack = when (it) {
                        HomeTabType.Portfolio -> portfolioBackStack
                        HomeTabType.Settings -> profileBackStack
                    }
                    while (stack.size > 1) stack.removeLast()
                }
            )
        }
    ) { innerPadding ->
        NavDisplay(
            modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding()),
            backStack = activeBackStack,
            entryProvider = entryProvider {
                entry<PortfolioTab> { PortfolioScreen() }
                entry<ProfileTab> { }
            }
        )
    }
}

@Composable
fun BottomBarNav3(
    selected: HomeTabType,
    onSelect: (HomeTabType) -> Unit,
    onReselect: (HomeTabType) -> Unit
) {
    NavigationBar {

        // Portfolio tab
        val isPortfolioSelected = selected == HomeTabType.Portfolio
        NavigationBarItem(
            selected = isPortfolioSelected,
            onClick = { if (isPortfolioSelected) onReselect(HomeTabType.Portfolio) else onSelect(HomeTabType.Portfolio) },
            icon = { Icon(Icons.Outlined.AccountBalance, contentDescription = "Portfolio") },
            label = { Text("Portfolio") }
        )

        // Settings tab
        val isSettingsSelected = selected == HomeTabType.Settings
        NavigationBarItem(
            selected = isSettingsSelected,
            onClick = { if (isSettingsSelected) onReselect(HomeTabType.Settings) else onSelect(HomeTabType.Settings) },
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}
