package com.ssepulveda.presentation_home.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.state.CommonUIEvent
import com.ssepulveda.presentation_common.state.UIEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onDestination: (route: String) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.submitAction(HomeUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            ContainerHome(
                it,
                snackBarHostState,
            ) { route ->
                onDestination(route)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is HomeSingleEvent.OpenAddBill -> {
                    onDestination(it.navRoute)
                }
            }
        }
    }

    var uiEvent by remember {
        mutableStateOf<UIEvent?>(null)
    }

    CommonUIEvent(uiEvent, snackBarHostState) {
        viewModel.hideEventFlow()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            if (uiEvent != it) {
                uiEvent = it
            }
        }
    }
}
