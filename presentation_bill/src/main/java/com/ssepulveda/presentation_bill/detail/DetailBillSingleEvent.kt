package com.ssepulveda.presentation_bill.detail

import com.ssepulveda.presentation_common.state.UiSingleEvent

sealed class DetailBillSingleEvent : UiSingleEvent {
    data object Close: DetailBillSingleEvent()

}
