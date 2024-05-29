package com.ssepulveda.presentation_report.ui

import com.ssepulveda.presentation_common.state.UiAction

sealed class ReportAction : UiAction {

    data object Load : ReportAction()
}
