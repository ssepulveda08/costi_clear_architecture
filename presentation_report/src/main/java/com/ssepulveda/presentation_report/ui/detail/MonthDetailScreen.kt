package com.ssepulveda.presentation_report.ui.detail

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssepulveda.presentation_common.navigation.MonthInput

@Composable
fun MonthDetailScreen(month: MonthInput) {
    Text(text = "Mes $month")
}

@Composable
@Preview
private fun PreviewMonthDetail() {
    MaterialTheme {
        MonthDetailScreen(MonthInput(4))
    }
}