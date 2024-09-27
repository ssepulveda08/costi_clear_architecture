package com.ssepulveda.costi.domain.entity

data class Account(
    val id: Int? = null,
    val month: Int,
    val name: String,
    val capped: Double,
)
