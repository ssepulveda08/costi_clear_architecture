package com.ssepulveda.costi.domain.entity

data class CostType(
    val id: Int,
    val name: String,
    val subTypes: List<SubTypeCost>? = null
)