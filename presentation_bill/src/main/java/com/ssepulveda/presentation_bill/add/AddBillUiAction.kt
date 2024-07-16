package com.ssepulveda.presentation_bill.add

import com.ssepulveda.presentation_common.state.UiAction

sealed class AddBillUiAction : UiAction {

    data object LoadData : AddBillUiAction()
    data class SelectedType(val type: ItemDropdown) : AddBillUiAction()
    data class SelectedSubType(val subType: ItemDropdown) : AddBillUiAction()
    data object AddBill : AddBillUiAction()
}