@file:Suppress("NAME_SHADOWING")

package com.ssepulveda.presentation_home.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.state.CommonUIEvent
import com.ssepulveda.presentation_common.state.UIEvent
import com.ssepulveda.presentation_common.ui.CustomToolbar
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.CarouselOfAccounts
import com.ssepulveda.presentation_home.home.ui.homeContainer.HomeContainer
import com.ssepulveda.presentation_home.home.ui.homeContainer.HomeContainerViewModel
import com.ssepulveda.presentation_home.home.ui.homeContainer.HomeSingleEvent
import com.ssepulveda.presentation_menu.Menu
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {

    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.submitAction(HomeUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Home(
                viewModel,
                navController,
                snackBarHostState,
            )
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is HomeSingleEvent.OpenAddBill -> {
                    //send account id for param
                    navController.navigate(it.navRoute)
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun Home(
    viewModel: HomeViewModel,
    navController: NavHostController,
    snackBarHostState: SnackbarHostState,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val tabs = viewModel.tabs.collectAsState()
    val containerViewModel = hiltViewModel<HomeContainerViewModel>()
    containerViewModel.setAccountID(tabs.value.firstOrNull { it.selected })


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(230.dp)
            ) { Menu(navController) }
        },
    ) {
        Scaffold(
            topBar = {
                CustomToolbar(
                    openMenu = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    },
                    openOptions = {
                        viewModel.submitAction(HomeUiAction.OpenDialogCloseMonth)
                    }
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing, //WindowInsets.statusBars,
            floatingActionButton = {
                if (snackBarHostState.currentSnackbarData == null) {
                    ExtendedFloatingActionButton(
                        onClick = {
                            /*viewModel.submitSingleEvent(
                                HomeSingleEvent.OpenAddBill(
                                    NavRoutes.Bill_Add.route
                                )
                            )*/
                            viewModel.openAddBill()
                        },
                        icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                        text = { Text(text = stringResource(id = R.string.copy_add_bill)) },
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(snackBarHostState)
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
                CarouselOfAccounts(
                    tabs.value,
                    onClickTab = { tab ->
                        //val page = tabs.value.indexOfFirst { it.id == tab.id } ?: 0
                        viewModel.submitAction(HomeUiAction.SelectTab(tab))
                       // containerViewModel.setAccountID(tab.id)
                        //id = tab.id
                        /*scope.launch {
                           // pagerState.animateScrollToPage(page = page)
                        }*/
                    },
                    onAddTab = {
                        viewModel.submitAction(HomeUiAction.OpenDialogAddAccount)
                    }
                )

                /* HorizontalPager(
                     pagerState,
                     userScrollEnabled = false
                 ) { */

                HomeContainer(
                    containerViewModel, navController
                )
                //} /
            }

        }
    }
}
