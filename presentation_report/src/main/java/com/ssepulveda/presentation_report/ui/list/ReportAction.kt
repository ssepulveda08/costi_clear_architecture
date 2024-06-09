package com.ssepulveda.presentation_report.ui.list

import com.ssepulveda.presentation_common.state.UiAction

sealed class ReportAction : UiAction {

    data object Load : ReportAction()
}
