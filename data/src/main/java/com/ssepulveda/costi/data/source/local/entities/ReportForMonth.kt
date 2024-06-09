package com.ssepulveda.costi.data.source.local.entities

data class ReportForMonth(
    val month: Int,
    val total: Double,
    val maxValue: Double,
    val minValue: Double,
)