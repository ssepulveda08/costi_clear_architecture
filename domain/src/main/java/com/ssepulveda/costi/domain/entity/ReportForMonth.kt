package com.ssepulveda.costi.domain.entity

data class ReportForMonth(
    val total: Double?,
    val reportForType: List<TotalValueByType> = listOf(),
)

data class TotalValueByType(
    val typeName:String?,
    val total: Double?
)
