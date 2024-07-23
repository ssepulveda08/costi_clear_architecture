package com.ssepulveda.presentation_report.ui.detail

import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.reports.GetReportMonthDetailUseCase
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthDetailViewModel @Inject constructor(
    private val getReportMonthDetailUseCase: GetReportMonthDetailUseCase
) : MviViewModel<MonthDetailModel, UiState<MonthDetailModel>, MonthDetailAction, MonthDetailSingleEvent>() {

    override fun initState(): UiState<MonthDetailModel> = UiState.Loading

    override fun handleAction(action: MonthDetailAction) {
        when (action) {
            is MonthDetailAction.LoadData -> loadData(action.idMonth)
        }
    }

    private fun loadData(idMonth: Int) {

        viewModelScope.launch {
            getReportMonthDetailUseCase.execute(GetReportMonthDetailUseCase.Request(idMonth))
                .collect {result ->
                    when (result) {
                        is Result.Error -> {
                            UiState.Error(result.exception.localizedMessage.orEmpty())
                        }

                        is Result.Success -> {
                            result.data.model?.let { detail ->
                                val model = MonthDetailModel(
                                    detail.month.id,
                                    detail.month.name,
                                    total = detail.bills?.sumOf { it.value }.toString() ?: "",
                                    detail.bills?.map { ItemBill(
                                        it.id ?: 0,
                                        it.description,
                                        it.value.toString(),
                                        it.recordDate
                                    ) }
                                )
                                submitState(UiState.Success(model))
                            }
                        }
                    }
                }
        }
    }

}