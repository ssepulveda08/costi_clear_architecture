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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssepulveda.costi.navHost.AppNavHost
import com.ssepulveda.costi.splash.SplashScreen
import com.ssepulveda.costi.splash.SplashViewModel
import com.ssepulveda.presentation_bill.add.AddBillScreen
import com.ssepulveda.presentation_bill.add.AddBillViewModel
import com.ssepulveda.presentation_bill.detail.DetailBillScreen
import com.ssepulveda.presentation_bill.detail.DetailBillViewModel
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.ui.CustomWebView
import com.ssepulveda.presentation_common.ui.theme.AppTheme
import com.ssepulveda.presentation_home.home.HomeScreen
import com.ssepulveda.presentation_home.home.HomeViewModel
import com.ssepulveda.presentation_report.ui.list.ReportScreen
import com.ssepulveda.presentation_report.ui.list.ReportViewModel
import com.ssepulveda.presentation_report.ui.detail.MonthDetailScreen
import com.ssepulveda.presentation_report.ui.detail.MonthDetailViewModel
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
                    AppNavHost()
                }
            }
        }
    }
}
