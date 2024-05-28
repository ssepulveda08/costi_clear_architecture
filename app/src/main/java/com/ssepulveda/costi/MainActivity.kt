package com.ssepulveda.costi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssepulveda.costi.splash.SplashScreen
import com.ssepulveda.costi.splash.SplashViewModel
import com.ssepulveda.presentation_add_bill.add.AddBillScreen
import com.ssepulveda.presentation_add_bill.add.AddBillViewModel
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.ui.CustomWebView
import com.ssepulveda.presentation_common.ui.theme.AppTheme
import com.ssepulveda.presentation_home.home.HomeScreen
import com.ssepulveda.presentation_home.home.HomeViewModel
import com.ssepulveda.presentation_report.ui.ReportScreen
import com.ssepulveda.presentation_welcome.welcome.WelcomeScreen
import com.ssepulveda.presentation_welcome.welcome.WelcomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController = navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: androidx.navigation.NavHostController) {
    NavHost(navController, startDestination = NavRoutes.Splash.route) {
        composable(route = NavRoutes.Splash.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel,
                navController
            )
        }
        composable(
            route = NavRoutes.Welcome.route,
            arguments = NavRoutes.Welcome.arguments
        ) {
            val viewModel = hiltViewModel<WelcomeViewModel>()
            WelcomeScreen(viewModel, navController)
        }
        composable(
            route = NavRoutes.Home.route,
            arguments = NavRoutes.Home.arguments
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel, navController)
        }
        composable(
            route = NavRoutes.Bills.route,
            arguments = NavRoutes.Bills.arguments
        ) {
            // TODO ADD BILLS
        }
        composable(
            route = NavRoutes.WebView.route,
            arguments = NavRoutes.WebView.arguments
        ) {
            Log.d("POTATO", NavRoutes.WebView.fromEntry(it))
            CustomWebView(url = NavRoutes.WebView.fromEntry(it), navController)
        }
        composable(
            route = NavRoutes.Bill_Add.route,
            arguments = NavRoutes.Bill_Add.arguments
        ) {
            val viewModel = hiltViewModel<AddBillViewModel>()
            AddBillScreen(viewModel, navController)
        }
        composable(
            route = NavRoutes.Months.route,
            arguments = NavRoutes.Months.arguments
        ) {
            ReportScreen(navController)
        }
    }
}