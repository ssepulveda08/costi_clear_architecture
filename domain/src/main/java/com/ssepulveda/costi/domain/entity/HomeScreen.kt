package com.ssepulveda.costi.domain.entity

data class HomeScreen(
    val idMonth:  Int,
    val bills: List<Bill>,
    val dataReport: List<ReportForMonth> = listOf(),
)