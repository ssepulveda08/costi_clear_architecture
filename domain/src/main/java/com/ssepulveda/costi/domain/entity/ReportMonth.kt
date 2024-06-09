package com.ssepulveda.costi.domain.entity

data class ReportMonth(
    val id: Int,
    val total: Double,
    val maxValue: Double?,
    val minValue: Double?,
)