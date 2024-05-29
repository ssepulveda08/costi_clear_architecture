package com.ssepulveda.presentation_home.home

import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.Month
import com.ssepulveda.costi.domain.useCase.DeleteBillUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.costi.domain.useCase.SaveCurrentMonthUseCase
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import com.ssepulveda.presentation_common.ui.StringResolve
import com.ssepulveda.presentation_home.R
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
    private val saveCurrentMonthUseCase: SaveCurrentMonthUseCase,
    private val stringResolve: StringResolve
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
                stringResolve.getString(R.string.title_update_month),
                stringResolve.getString(R.string.description_update_month),
                textCancel = stringResolve.getString(R.string.copy_close),
                textSuccess = stringResolve.getString(R.string.copy_continue),
                onCancel = { hideDialog() },
                onSuccess = { updateMonth(getCurrentMonth()) }
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
                        title = stringResolve.getString(R.string.title_successful_process),
                        description = stringResolve.getString(R.string.description_delete_bill),
                        textSuccess = stringResolve.getString(R.string.copy_ok),
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

    private fun updateMonth(month: Int) {
        hideDialog()
        submitState(UiState.Loading)
        viewModelScope.launch {
            saveCurrentMonthUseCase.execute(SaveCurrentMonthUseCase.Request(month))
                .collect {
                    loadData()
                }
        }
    }

    override fun initState(): UiState<HomeModel> = UiState.Loading

    private fun showDeletionConfirmation(bill: BillModel) {
        submitDialog(
            Dialog.DialogDefault(
                title = stringResolve.getString(R.string.title_detele_bill),
                description = stringResolve.getString(
                    R.string.title_detele_description,
                    bill.description
                ),
                textSuccess = stringResolve.getString(R.string.copy_delete),
                textCancel = stringResolve.getString(R.string.copy_cancel),
                onSuccess = { deleteBill(bill) },
                onCancel = { hideDialog() }
            )
        )
    }

    private fun showDialogCloseMonth() {
        submitDialog(Dialog.DialogDefault(
            title = stringResolve.getString(R.string.title_close_month),
            description = stringResolve.getString(R.string.description_close_month),
            textCancel = stringResolve.getString(R.string.copy_cancel),
            textSuccess = stringResolve.getString(R.string.copy_continue),
            onCancel = { hideDialog() },
            onSuccess = { updateMonth(getCurrentMonth()+1) }
        ))
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Load -> loadData()
            is HomeUiAction.DeleteBill -> showDeletionConfirmation(action.bill)
            is HomeUiAction.UpdateMonth -> updateMonth(getCurrentMonth())
            is HomeUiAction.OpenDialogCloseMonth -> showDialogCloseMonth()
        }
    }

}