package com.ssepulveda.presentation_home.home

import com.ssepulveda.presentation_common.state.UiAction
import com.ssepulveda.presentation_home.home.ui.account.TabsAccount

sealed class HomeUiAction : UiAction {
    data object Load: HomeUiAction()
    data object UpdateMonth: HomeUiAction()
    data object OpenDialogAddAccount: HomeUiAction()
    data object OpenDialogCloseMonth: HomeUiAction()
    //data object AddAccount: HomeUiAction()
    class SelectTab(val tab: TabsAccount): HomeUiAction()

   // data class DeleteBill(val bill: BillModel): HomeUiAction()
}