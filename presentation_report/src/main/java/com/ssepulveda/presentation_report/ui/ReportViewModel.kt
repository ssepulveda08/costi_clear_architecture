package com.ssepulveda.presentation_report.ui

import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.useCase.GetReportMonthsUseCase
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getReportMonthsUseCase: GetReportMonthsUseCase,
    private val converter: ReportConverter,
) : MviViewModel<ReportModel, UiState<ReportModel>, ReportAction, ReportSingleEvent>() {

    private fun loadData() {
        viewModelScope.launch {
            getReportMonthsUseCase.execute(GetReportMonthsUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    override fun initState(): UiState<ReportModel> = UiState.Loading

    override fun handleAction(action: ReportAction) {
        when (action) {
            ReportAction.Load -> loadData()
        }
    }
}
