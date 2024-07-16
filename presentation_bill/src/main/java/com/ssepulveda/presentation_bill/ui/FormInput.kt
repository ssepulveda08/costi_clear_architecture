package com.ssepulveda.presentation_bill.ui

import com.ssepulveda.presentation_bill.add.ItemDropdown

data class FormInput(
    var types: List<ItemDropdown>,
    var subTypes: List<ItemDropdown>,
    var subTypeSelect: ItemDropdown? = null,
    var value: String = "",
    var description: String = ""
) {
    fun isCompleted(): Boolean = subTypeSelect != null &&
            value.isNotBlank() &&
            description.isNotBlank()
}