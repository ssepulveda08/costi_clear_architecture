package com.ssepulveda.presentation_home.home.ui.account

data class AccountModel(
    //val accounts: List<TabsAccount>
    val success: Boolean
)

data class TabsAccount(
    val id: Int,
    val name: String,
    val selected: Boolean,
    val capped: Double,
)