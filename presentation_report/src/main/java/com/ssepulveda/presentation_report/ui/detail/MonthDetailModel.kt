package com.ssepulveda.presentation_report.ui.detail

data class MonthDetailModel(
    val id: Int,
    val name: String,
    val total: String,
    val bills: List<ItemBill>?
)

data class ItemBill(
    val id: Int,
    val description: String,
    val value : String,
    val date : String,
)