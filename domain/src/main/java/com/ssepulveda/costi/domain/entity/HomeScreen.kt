package com.ssepulveda.costi.domain.entity

data class HomeScreen(
    val idMonth:  Int,
    val totalMonth:  Double,
    val bills: List<Bill>,
    val dataReportType: List<TotalValueByType> = listOf(),
    val dataReportWeed: List<CurrentWeekReport> = listOf(),
    val daysOfWeek: List<DayOfWeek> = listOf()
)