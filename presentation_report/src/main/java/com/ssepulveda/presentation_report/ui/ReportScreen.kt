package com.ssepulveda.presentation_report.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_common.ui.DialogController
import com.ssepulveda.presentation_common.ui.toCurrencyFormat
import com.ssepulveda.presentation_report.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ReportScreen(
    viewModel: ReportViewModel,
    navController: NavHostController?
) {

    LaunchedEffect(Unit) {
        viewModel.submitAction(ReportAction.Load)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Report(it, navController)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Report(
    model: ReportModel?,
    navController: NavHostController?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.copy_report)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                }
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
                ItemMonth(it, localCode)
            }
        }
    }
}

@Composable
private fun ItemMonth(monthData: MonthData, localCode: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = monthData.label,
                style = MaterialTheme.typography.titleLarge
            )
            val total = if (monthData.total > 0) {
                monthData.total.toCurrencyFormat(localCode)
            } else {
                stringResource(R.string.copy_no_record)
            }
            Text(
                text = total,
                style = MaterialTheme.typography.labelLarge
            )
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
