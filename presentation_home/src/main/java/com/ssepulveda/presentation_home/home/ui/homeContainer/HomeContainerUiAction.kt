package com.ssepulveda.presentation_home.home.ui.homeContainer

import com.ssepulveda.presentation_common.state.UiAction

sealed class HomeContainerUiAction : UiAction {
    data object Load: HomeContainerUiAction()
    data object UpdateMonth: HomeContainerUiAction()
    //data object OpenDialogCloseMonth: HomeContainerUiAction()
    data class DeleteBill(val bill: BillModel): HomeContainerUiAction()
}