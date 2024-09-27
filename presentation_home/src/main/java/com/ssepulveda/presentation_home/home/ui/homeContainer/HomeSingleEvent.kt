package com.ssepulveda.presentation_home.home.ui.homeContainer

import com.ssepulveda.presentation_common.state.UiSingleEvent

sealed class HomeSingleEvent : UiSingleEvent {
    data class OpenAddBill(val navRoute: String) : HomeSingleEvent()
   /* data object OpenDialog : HomeSingleEvent()
    data object HIdeDialog : HomeSingleEvent()*/
}