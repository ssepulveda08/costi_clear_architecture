package com.ssepulveda.presentation_home.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

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
}

@Composable
private fun Home(viewModel: HomeViewModel, homeModel: HomeModel?) {

    val nameMoth = homeModel?.nameMoth ?: "Sin Datos"
    Scaffold(
        topBar = {
        },
        bottomBar = {
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    viewModel.submitSingleEvent(HomeSingleEvent.OpenAddBill(
                        NavRoutes.Bill_Add.route
                    ))
                },
                icon = { Icon(Icons.Filled.Add, "Extended floating action button.") },
                text = { Text(text = "Add Gasto") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                nameMoth,
                modifier = Modifier.padding(16.dp),
                fontSize = 32.sp
            )
            LineChart(
                linesChartData = listOf(
                    LineChartData(
                        points = homeModel?.report ?: listOf(),
                        lineDrawer = SolidLineDrawer(),
                    )
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(start = 54.dp, end = 16.dp),
                animation = simpleChartAnimation(),
                pointDrawer = FilledCircularPointDrawer(),
                horizontalOffset = 5f,
            )
            Bills(homeModel?.bills)

        }

    }
}

@Composable
private fun Bills(bills: List<BillModel>?) {
    bills?.let { list ->
        LazyColumn {
            items(list) {
                ItemBill(it)
            }
        }
    }
}

@Composable
private fun ItemBill(billModel: BillModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start =  16.dp, end =  16.dp, bottom =  16.dp)
            .border(
                width = 1.dp, color = Color.Cyan, shape = RoundedCornerShape(15.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = billModel.description,
                fontSize =  20.sp,
                modifier = Modifier)
            Spacer(modifier = Modifier.padding(2.dp))
            Text(text = billModel.value.toString(),
                fontSize =  16.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier)
        }
    }
}