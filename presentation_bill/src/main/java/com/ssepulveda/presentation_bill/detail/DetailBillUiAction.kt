package com.ssepulveda.presentation_bill.detail

import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.state.UiAction

sealed class DetailBillUiAction : UiAction {

    data class LoadData(val input : DetailInput) : DetailBillUiAction()
}