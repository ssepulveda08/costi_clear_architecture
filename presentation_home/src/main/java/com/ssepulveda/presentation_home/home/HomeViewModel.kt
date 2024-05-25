package com.ssepulveda.presentation_home.home

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
                textCancel = "Cerrar",
                textSuccess = "Actualizar",
                onCancel = { hideDialog() },
                onSuccess = { updateMonth() }
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
                        month = 0,
                        date = bill.date
                    )
                )
            ).collect {
                submitDialog(
                    Dialog.DialogDefault(
                        title = "Proceso Exitoso!",
                        description = "Se elimino Correctamente el Item",
                        textSuccess = "OK",
                        onSuccess = ::closeDialogDeletedBill,
                        onCancel = ::closeDialogDeletedBill
                    )
                )
            }
        }
    }

    private fun closeDialogDeletedBill() {
        hideDialog()
        loadData()
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

    private fun showDeletionConfirmation(bill: BillModel) {
        submitDialog(
            Dialog.DialogDefault(
                title = "Confirmar accion!",
                description = "Esta seguro de eliminar el gasto: ${bill.description},\n\nUna vez eliminado no podra revertir los cambios",
                textSuccess = "Eliminar",
                textCancel = "Cancelar",
                onSuccess = { deleteBill(bill) },
                onCancel = { hideDialog() }
            )
        )
    }

    private fun showDialogOpenMonth() {
        submitDialog(Dialog.DialogDefault(
            "Aviso Importante",
            "Esta a punto de cerrar el mes [Mes], una vez realices esta accion no podrar agregas mas gastos a este mes",
            textCancel = "Cancelar",
            textSuccess = "Continuar",
            onCancel = { hideDialog() },
            onSuccess = { updateMonth() }
        ))
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Load -> loadData()
            is HomeUiAction.DeleteBill -> showDeletionConfirmation(action.bill)
            is HomeUiAction.UpdateMonth -> updateMonth()
            is HomeUiAction.OpenDialogCloseMonth -> showDialogOpenMonth()
        }
    }

}