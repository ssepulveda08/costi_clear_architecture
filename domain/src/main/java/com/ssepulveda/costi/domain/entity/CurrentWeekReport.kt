package com.ssepulveda.costi.domain.entity

data class CurrentWeekReport(
    val dayOfWeek: Int,
    val date: String,
    val total: Double
)