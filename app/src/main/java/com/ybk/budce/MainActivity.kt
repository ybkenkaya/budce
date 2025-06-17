// ============================================================================
// MainActivity.kt – single‑activity entry point (clean version) – v3
// ----------------------------------------------------------------------------
// • Uses @AndroidEntryPoint so screens/ViewModels can use Hilt later.
// • Applies BudceTheme and hosts the MainNavHost() composable.
// • No other UI logic lives here – everything is delegated to composables in
//   the ui/ layer.
// ----------------------------------------------------------------------------
package com.ybk.budce

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ybk.budce.ui.navigation.MainNavHost
import com.ybk.budce.ui.theme.BudceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudceTheme {
                MainNavHost()   // bottom navigation + all screens
            }
        }
    }
}
