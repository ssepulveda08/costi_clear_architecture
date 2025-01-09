package com.ssepulveda.costi.navHost

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssepulveda.costi.splash.SplashScreen
import com.ssepulveda.costi.splash.SplashViewModel
import com.ssepulveda.presentation_bill.add.AddBillScreen
import com.ssepulveda.presentation_bill.add.AddBillViewModel
import com.ssepulveda.presentation_bill.detail.DetailBillScreen
import com.ssepulveda.presentation_bill.detail.DetailBillViewModel
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.ui.CustomWebView
import com.ssepulveda.presentation_home.home.HomeScreen
import com.ssepulveda.presentation_home.home.HomeViewModel
import com.ssepulveda.presentation_report.ui.detail.MonthDetailScreen
import com.ssepulveda.presentation_report.ui.detail.MonthDetailViewModel
import com.ssepulveda.presentation_report.ui.list.ReportScreen
import com.ssepulveda.presentation_report.ui.list.ReportViewModel
import com.ssepulveda.presentation_welcome.welcome.WelcomeScreen
import com.ssepulveda.presentation_welcome.welcome.WelcomeViewModel


@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavRoutes.Splash.route) {
        composable(route = NavRoutes.Splash.route) {
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(
                viewModel
            ) {
                navController.navigate(it)
            }
        }
        composable(
            route = NavRoutes.Welcome.route,
            arguments = NavRoutes.Welcome.arguments
        ) {
            val viewModel = hiltViewModel<WelcomeViewModel>()
            WelcomeScreen(viewModel) {
                navController.navigate(it)
            }
        }
        composable(
            route = NavRoutes.Home.route,
            arguments = NavRoutes.Home.arguments
        ) {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(viewModel) {
                navController.navigate(it)
            }
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
            // Log.d("POTATO", NavRoutes.WebView.fromEntry(it))
            CustomWebView(url = NavRoutes.WebView.fromEntry(it), navController)
        }
        composable(
            route = NavRoutes.Bill_Add.route,
            arguments = NavRoutes.Bill_Add.arguments
        ) {
            val viewModel = hiltViewModel<AddBillViewModel>()
            AddBillScreen(viewModel) {
                navController.navigateUp()
            }
        }
        composable(
            route = NavRoutes.Months.route,
            arguments = NavRoutes.Months.arguments
        ) {
            val viewModel = hiltViewModel<ReportViewModel>()
            ReportScreen(viewModel, onNavigate = {
                navController.navigate(it)
            }, onBack = {
                navController.navigateUp()
            })
        }
        composable(
            route = NavRoutes.DetailBill.route,
            arguments = NavRoutes.DetailBill.arguments
        ) {
            val viewModel = hiltViewModel<DetailBillViewModel>()
            DetailBillScreen(
                viewModel = viewModel,
                input = NavRoutes.DetailBill.fromEntry(it),
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = NavRoutes.DetailMonth.route,
            arguments = NavRoutes.DetailMonth.arguments
        ) {
            val viewModel = hiltViewModel<MonthDetailViewModel>()
            MonthDetailScreen(
                NavRoutes.DetailMonth.fromEntry(it),
                viewModel,
                onBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}