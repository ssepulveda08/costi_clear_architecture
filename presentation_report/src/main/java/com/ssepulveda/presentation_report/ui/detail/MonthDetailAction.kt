package com.ssepulveda.presentation_report.ui.detail

import com.ssepulveda.presentation_common.state.UiAction

sealed class MonthDetailAction : UiAction {

    data class LoadData(val idMonth: Int) : MonthDetailAction()
}