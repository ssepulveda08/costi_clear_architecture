package com.ssepulveda.costi.domain.entity

data class Bill(
    val id: Int? = null,
    val subType: Int,
    val description: String,
    val value: Double,
    val month: Int,
    val recordDate: String,
    val updateDate: String,

)