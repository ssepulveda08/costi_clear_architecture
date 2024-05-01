package com.ssepulveda.presentation_home.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.Month
import com.ssepulveda.costi.domain.useCase.DeleteBillUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.costi.domain.useCase.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.UpdateInitialConfigurationUseCase
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeInformationUseCase: GetHomeInformationUseCase,
    private val converter: HomerResultConverter,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val saveCurrentMonthUseCase: SaveCurrentMonthUseCase
) : MviViewModel<HomeModel, UiState<HomeModel>, HomeUiAction, HomeSingleEvent>() {

    private fun loadData() {
        viewModelScope.launch {
            getHomeInformationUseCase.execute(GetHomeInformationUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    private fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    private fun deleteBill(bill: BillModel) {
        viewModelScope.launch {
            deleteBillUseCase.execute(
                DeleteBillUseCase.Request(
                    Bill(
                        id = bill.id,
                        subType = bill.typeId,
                        description = bill.description,
                        value = bill.value,
                        month = 0
                    )
                )
            ).collect {
                loadData()
            }
        }
    }

    private fun updateMonth() {
        viewModelScope.launch {
            submitState(UiState.Loading)
            saveCurrentMonthUseCase.execute(SaveCurrentMonthUseCase.Request(getCurrentMonth())).collect {
                loadData()
            }
        }
    }

    override fun initState(): UiState<HomeModel> = UiState.Loading

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Load -> loadData()
            is HomeUiAction.DeleteBill -> deleteBill(action.bill)
            is HomeUiAction.UpdateMonth -> updateMonth()
        }
    }

}