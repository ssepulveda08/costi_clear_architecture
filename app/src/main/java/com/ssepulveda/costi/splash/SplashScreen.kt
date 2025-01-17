package com.ssepulveda.costi.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    onDestination : (String) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(SplashUiAction.ValidateConfiguration)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {

        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when(it) {
                is SplashSingleEvent.OpenWelcomeScreen -> {
                    onDestination.invoke(it.navRoute)
                }
                is SplashSingleEvent.OpenHomeScreen -> {
                    onDestination.invoke(it.navRoute)
                }
            }
        }
    }
}
