package com.ssepulveda.presentation_report.ui.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.ui.SingleToolbar
import com.ssepulveda.presentation_common.ui.toCurrencyFormat
import com.ssepulveda.presentation_report.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReportScreen(
    viewModel: ReportViewModel,
    onNavigate: (route: String) -> Unit,
    onBack: () -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.submitAction(ReportAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Report(it, onNavigate, onBack)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {

        }
    }

}

@Composable
private fun Report(
    model: ReportModel?,
    onNavigate: (route: String) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            SingleToolbar(
                title = stringResource(R.string.copy_report),
                onBack
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
        },
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues
        ) {
            val localCode = model?.localCode ?: "Cop"
            items(model?.months ?: listOf()) {
                ItemMonth(it, localCode) { month ->
                    Log.d("POTATO", "Route ${NavRoutes.DetailMonth.routeForMonth(month)}")
                    onNavigate(
                        NavRoutes.DetailMonth.routeForMonth(month)
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemMonth(
    monthData: MonthData,
    localCode: String,
    onClick: (month: Int) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
            .clickable {
                if (monthData.total > 0) {
                    onClick.invoke(monthData.id)
                }
            }
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            var addMaxAndMin by remember { mutableStateOf(false) }
            Text(
                text = monthData.label,
                style = MaterialTheme.typography.titleLarge
            )
            val total = if (monthData.total > 0) {
                val value = monthData.total.toCurrencyFormat(localCode)
                addMaxAndMin = true
                stringResource(id = R.string.copy_total, value)
            } else {
                stringResource(R.string.copy_no_record)
            }
            Text(
                text = total,
                style = MaterialTheme.typography.labelLarge
            )
            if (addMaxAndMin) {
                Text(
                    text = stringResource(id = R.string.copy_max, monthData.maxValue),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = stringResource(id = R.string.copy_min, monthData.maxValue),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewReportScreen() {
    MaterialTheme {
        //Report(null)
    }
}
