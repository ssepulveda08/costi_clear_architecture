package com.ssepulveda.presentation_home.home

import com.ssepulveda.presentation_home.home.ui.charts.Bar
import com.ssepulveda.presentation_home.home.ui.charts.CircleChart

data class HomeModel(
    val idMonth: Int,
    val nameMonth: String,
    val totalMonth: Double,
    val maxValue: Double,
    val minValue: Double,
    val currentDate: String,
    val isCurrentMonthHigher: Boolean,
    val bills: List<BillModel>,
    val reportForType: List<CircleChart> = listOf(),
    val reportForWeek: List<Bar> = listOf(),
    val localCode: String = "COP",
    val currentWeek: Int = 0,
    val weeklyReport: List<WeeklyReport> = listOf(),
)

data class BillModel(
    val id: Int,
    val value: Double,
    val description: String,
    val typeId: Int,
    val nameType: String,
    val date: String,
)


data class WeeklyReport(
    val week: Int,
    val startDateWeed: String,
    val endDateWeed: String,
    val days: List<Bar> = listOf()
)

