package com.ssepulveda.presentation_bill.add

import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.state.UiAction

sealed class AddBillUiAction : UiAction {

    data object LoadData : AddBillUiAction()
    data class AddBill(val input: FormInput) : AddBillUiAction()
}