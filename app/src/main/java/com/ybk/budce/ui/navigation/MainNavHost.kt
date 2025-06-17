// ============================================================================
// MainNavHost.kt  – Bottom‑navigation scaffold (padding fixed) – v3
// ============================================================================
package com.ybk.budce.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.ybk.budce.R
import com.ybk.budce.ui.screens.*

// ----------------------------------------------------------------------------
// Bottom‑bar destination definitions
// ----------------------------------------------------------------------------
sealed class BottomNavItem(
    val route: String,
    val labelRes: Int,
    val icon: ImageVector
) {
    data object Dashboard    : BottomNavItem("dashboard",    R.string.nav_dashboard,    Icons.Filled.Home)
    data object Transactions : BottomNavItem("transactions", R.string.nav_transactions, Icons.Filled.List)
    data object Budget       : BottomNavItem("budget",       R.string.nav_budget,       Icons.Filled.PieChart)
    data object Accounts     : BottomNavItem("accounts",     R.string.nav_accounts,     Icons.Filled.AccountBalanceWallet)
}

// ----------------------------------------------------------------------------
// Root composable hosting NavHost inside a Scaffold.
// ----------------------------------------------------------------------------
@Composable
fun MainNavHost() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BudceBottomBar(navController) }
    ) { innerPadding ->
        // Apply Scaffold padding to NavHost via Modifier
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Dashboard.route)    { DashboardScreen(padding = innerPadding) }
            composable(BottomNavItem.Transactions.route) { TransactionScreen(padding = innerPadding) }
            composable(BottomNavItem.Budget.route)       { BudgetScreen(padding = innerPadding) }
            composable(BottomNavItem.Accounts.route)     { AccountsScreen(padding = innerPadding) }
        }
    }
}

// ----------------------------------------------------------------------------
// Bottom‑navigation bar
// ----------------------------------------------------------------------------
@Composable
private fun BudceBottomBar(navController: NavHostController) {
    val items = listOf(
        BottomNavItem.Dashboard,
        BottomNavItem.Transactions,
        BottomNavItem.Budget,
        BottomNavItem.Accounts
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Avoid building up a large back‑stack when switching tabs
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(stringResource(id = item.labelRes)) }
            )
        }
    }
}
