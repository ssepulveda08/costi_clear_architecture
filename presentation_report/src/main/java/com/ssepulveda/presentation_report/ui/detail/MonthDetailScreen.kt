package com.ssepulveda.presentation_report.ui.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.inputs.MonthInput
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.ui.SingleToolbar
import com.ssepulveda.presentation_common.ui.toCurrencyFormat
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MonthDetailScreen(
    month: MonthInput,
    viewModel: MonthDetailViewModel,
    navController: NavHostController?
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(MonthDetailAction.LoadData(month.monthId))
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            it?.let {
                DetailView(it, navController)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {

        }
    }

    /* viewModel.showSingleDialog.collectAsState().value.let { dialog ->
         DialogController(dialog)
     }*/

}

@Composable
private fun DetailView(monthDetailModel: MonthDetailModel, navController: NavHostController?) {
    Scaffold(
        topBar = {
            SingleToolbar(title = monthDetailModel.name, navController = navController)
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
        ) {

            item {
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp).padding(top = 24.dp),
                    text = "Total: ${monthDetailModel.total.toCurrencyFormat("COP")}",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.padding(16.dp))
            }
            monthDetailModel.bills?.let {
                items(it) { item ->
                    ItemBillComponent(item = item)
                }
            }
        }
    }
}


@Composable
private fun ItemBillComponent(item: ItemBill) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = item.description,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = item.value.toCurrencyFormat("COP"),
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = item.date,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
@Preview
private fun PreviewMonthDetail() {
    MaterialTheme {
        MonthDetailScreen(MonthInput(4), viewModel(), null)
    }
}