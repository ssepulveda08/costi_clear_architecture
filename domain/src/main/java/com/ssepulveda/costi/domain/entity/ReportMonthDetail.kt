package com.ssepulveda.costi.domain.entity


data class ReportMonthDetail(
    val month: Month,
    val bills: List<Bill>?
)
