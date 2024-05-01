package com.ssepulveda.presentation_home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.ui.AlertDialogExample
import com.ssepulveda.presentation_common.ui.CustomToolbar
import com.ssepulveda.presentation_home.home.ui.GraphicsSection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {

    var openAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.submitAction(HomeUiAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            openAlertDialog = it?.isCurrentMonthHigher ?: false
            Home(viewModel, it)
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

    if (openAlertDialog) {
        AlertDialogExample(
            "Aviso Importante",
            "Actualmente el mes ya cambio, si quieres actualizar el mes seleccionado oprime continuar, si queires continuar en el mes $[mens] cierra el dialogo",
            Icons.Filled.AccountBox,
            onDismissRequest = {
                openAlertDialog = false
            },
            onConfirmation = {
                viewModel.submitAction(HomeUiAction.UpdateMonth)
                openAlertDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Home(viewModel: HomeViewModel, homeModel: HomeModel?) {
    val nameMonth = homeModel?.nameMonth ?: "Sin Datos"
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet { Text(text = "Menu") }
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
                    }
                )
            },
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
                    text = { Text(text = "Add Gasto") },
                )
            }
        ) { innerPadding ->

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background),
                        // verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(
                            nameMonth,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        Text(
                            "Total Mes: $ ${homeModel?.totalMonth?.toInt() ?: 0}",
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            homeModel?.currentDate.orEmpty(),
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        GraphicsSection(homeModel)
                        Spacer(modifier = Modifier.padding(8.dp))
                    }

                }
                items(homeModel?.bills ?: listOf()) {
                    ItemBill(it) { model ->
                        viewModel.submitAction(HomeUiAction.DeleteBill(model))
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemBill(billModel: BillModel, onDelete: (BillModel) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RandomCircularIcon()
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = billModel.description.capitalize(Locale.current),
                    fontSize = 20.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = "$ ${billModel.value.toInt()}",
                    fontSize = 16.sp,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        onDelete.invoke(billModel)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RandomCircularIcon(modifier: Modifier = Modifier) {
    val iconsList = listOf(
        Icons.Filled.DateRange,
        Icons.Filled.AccountCircle,
        Icons.Filled.Face,
        Icons.Filled.Favorite,
        Icons.Filled.ShoppingCart,
        Icons.Filled.MailOutline,
    )

    val randomIndex = Random.nextInt(iconsList.size)
    val randomIcon = iconsList[randomIndex]
    Box(
        modifier = modifier
            .size(45.dp)
            .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = randomIcon,
            contentDescription = null,
            tint = Color.White
        )
    }
}
