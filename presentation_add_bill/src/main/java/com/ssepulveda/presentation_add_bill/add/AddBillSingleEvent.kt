package com.ssepulveda.presentation_add_bill.add

import com.ssepulveda.presentation_common.state.UiSingleEvent

sealed class AddBillSingleEvent : UiSingleEvent {

    data object AddBill: AddBillSingleEvent()
    data object Close: AddBillSingleEvent()
}