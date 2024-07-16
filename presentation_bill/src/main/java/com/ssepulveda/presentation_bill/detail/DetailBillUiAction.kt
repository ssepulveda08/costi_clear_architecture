package com.ssepulveda.presentation_bill.detail

import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.UiAction

sealed class DetailBillUiAction : UiAction {

    data class LoadData(val input : DetailInput) : DetailBillUiAction()
    data class UpdateBill(val bill : FormInput) : DetailBillUiAction()
}