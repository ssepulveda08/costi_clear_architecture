package com.ssepulveda.presentation_bill.ui

import com.ssepulveda.presentation_bill.add.ItemDropdown

data class FormInput(
    var types: List<ItemDropdown>,
    var subTypes: List<ItemDropdown>,
    val idBill: Int? = null,
    var typeSelect: ItemDropdown? = null,
    var subTypeSelect: ItemDropdown? = null,
    var month: Int? = null,
    var value: String = "",
    var description: String = "",
    var recordDate: String? = null,
    var updateDate: String? = null
) {
    fun isCompleted(): Boolean = subTypeSelect != null &&
            value.isNotBlank() &&
            description.isNotBlank()
}