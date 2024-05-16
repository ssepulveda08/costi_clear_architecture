package com.ssepulveda.presentation_home.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.useCase.DeleteBillUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.costi.domain.useCase.SaveCurrentMonthUseCase
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
                validateCurrentMonth(it)
                submitState(it)
            }
        }
    }

    private fun validateCurrentMonth(uiState: UiState<HomeModel>) {
        if (uiState is UiState.Success && uiState.data.isCurrentMonthHigher) {
            submitDialog(Dialog.DialogDefault(
                "Aviso Importante",
                "Actualmente el mes ya cambio, si quieres actualizar el mes seleccionado oprime continuar, si queires continuar en el mes $[mens] cierra el dialogo",
                { hideDialog() },
                { updateMonth() }
            ))
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
                //loadData()
                submitDialog(Dialog.DialogDefault(
                    "Proceso Exitoso!",
                    "Se elimino Correctamente el Item",
                    {
                        loadData()
                        hideDialog()
                    },
                    {
                        hideDialog()
                        loadData()
                    }
                ))
            }
        }
    }

    private fun updateMonth() {
        hideDialog()
        submitState(UiState.Loading)
        viewModelScope.launch {
            saveCurrentMonthUseCase.execute(SaveCurrentMonthUseCase.Request(getCurrentMonth()))
                .collect {
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