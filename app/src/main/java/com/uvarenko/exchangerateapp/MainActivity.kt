package com.uvarenko.exchangerateapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uvarenko.exchangerateapp.ui.currency.CurrencyScreen
import com.uvarenko.exchangerateapp.ui.home.HomeScreen
import com.uvarenko.exchangerateapp.ui.theme.ExchangeRateAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeRateAppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable(route = "home") {
                        HomeScreen(addCurrency = { navController.navigate("currencies") })
                    }
                    composable(route = "currencies") {
                        CurrencyScreen(goBack = navController::popBackStack)
                    }
                }
            }
        }
    }

}
