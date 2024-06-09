package com.ssepulveda.presentation_report.ui.list

data class ReportModel(
    val months: List<MonthData>,
    val localCode: String = "COP"
)

data class MonthData(
    val id: Int,
    val label: String,
    val total: Double,
    val maxValue: Double,
    val minValue: Double,
)