package com.ssepulveda.presentation_home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.ui.CustomToolbar
import com.ssepulveda.presentation_common.ui.DialogController
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.GraphicsSection
import com.ssepulveda.presentation_home.home.ui.HeaderSection
import com.ssepulveda.presentation_home.home.ui.ItemBill
import com.ssepulveda.presentation_menu.Menu
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {

    LaunchedEffect(Unit) {
        viewModel.submitAction(HomeUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Home(viewModel, it, navController)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is HomeSingleEvent.OpenAddBill -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }

    viewModel.showSingleDialog.collectAsState().value.let { dialog ->
        DialogController(dialog)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(
    viewModel: HomeViewModel,
    homeModel: HomeModel?,
    navController: NavHostController,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
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
            bottomBar = {
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = {
                        viewModel.submitSingleEvent(
                            HomeSingleEvent.OpenAddBill(
                                NavRoutes.Bill_Add.route
                            )
                        )
                    },
                    icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                    text = { Text(text = stringResource(id = R.string.copy_add_bill)) },
                )
            }
        ) { innerPadding ->

            LazyColumn(
                contentPadding = innerPadding,
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background),
                    ) {

                        Spacer(modifier = Modifier.padding(4.dp))
                        HeaderSection(homeModel)
                        Spacer(modifier = Modifier.padding(4.dp))
                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.inversePrimary, thickness = 0.5.dp
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        GraphicsSection(homeModel)

                        Spacer(modifier = Modifier.padding(8.dp))

                        HorizontalDivider(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.inversePrimary,
                            thickness = 0.5.dp
                        )
                        Text(
                            text = stringResource(id = R.string.copy_list_bills),
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .padding(vertical = 4.dp)
                                .fillMaxHeight(),
                            style = MaterialTheme.typography.titleSmall,
                        )

                        Spacer(modifier = Modifier.padding(4.dp))
                    }

                }
                items(homeModel?.bills ?: listOf()) {
                    ItemBill(it, homeModel?.localCode ?: "COP") { model ->
                        viewModel.submitAction(HomeUiAction.DeleteBill(model))
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(30.dp))
                }
            }
        }
    }
}
