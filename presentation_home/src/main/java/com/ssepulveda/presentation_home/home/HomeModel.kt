package com.ssepulveda.presentation_home.home

import com.github.tehras.charts.line.LineChartData

data class HomeModel(
    val nameMoth: String,
    val bills: List<BillModel>,
    val report: List<LineChartData.Point> = listOf(),
)

data class BillModel(
    val id: Int,
    val value: Double,
    val description: String,
    val typeId: Int,
    val nameType: String,
)
