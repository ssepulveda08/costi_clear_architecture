package com.ssepulveda.presentation_home.home.ui.homeContainer

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Account
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.useCase.bill.DeleteBillUseCase
import com.ssepulveda.costi.domain.useCase.config.SaveCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.reports.GetHomeInformationUseCase
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.presentation_common.di.EventsView
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UIEvent
import com.ssepulveda.presentation_common.state.UiState
import com.ssepulveda.presentation_common.ui.StringResolve
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.account.TabsAccount
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeContainerViewModel  @Inject constructor(
    private val getHomeInformationUseCase: GetHomeInformationUseCase,
    private val converter: HomerResultConverter,
    private val deleteBillUseCase: DeleteBillUseCase,
    private val saveCurrentMonthUseCase: SaveCurrentMonthUseCase,
    private val stringResolve: StringResolve,
    private val eventsView: EventsView,
) : MviViewModel<HomeModel, UiState<HomeModel>, HomeContainerUiAction, HomeSingleEvent>() {


    private var account: TabsAccount? = null

    fun setAccountID(tab: TabsAccount?) {
        tab?.let {
            viewModelScope.launch {
                account = it
                submitState(UiState.Loading)
                loadData()
            }
        }

    }

    private fun loadData() {
        Log.d("POTATO", "HomeContainerViewModel LoadData $account")
        account?.let {
            viewModelScope.launch {
                getHomeInformationUseCase.execute(GetHomeInformationUseCase.Request(it.id)).map {
                    converter.convert(it)
                }.collect {
                    validateCurrentMonth(it)
                    submitState(it)
                }
            }
        } ?: run {
            submitState(UiState.Error("problemas en el sistema"))
        }
    }

    // todo Remove to home
    private fun validateCurrentMonth(uiState: UiState<HomeModel>) {
        if (uiState is UiState.Success && uiState.data.isCurrentMonthHigher) {
            val dialog = Dialog.DialogDefault(
                stringResolve.getString(R.string.title_update_month),
                stringResolve.getString(R.string.description_update_month),
                textCancel = stringResolve.getString(R.string.copy_close),
                textSuccess = stringResolve.getString(R.string.copy_continue),
                onCancel = { hideEventFlow() },
                onSuccess = { updateMonth(getCurrentMonth()) }
            )
            viewModelScope.launch {
                eventsView.submitEventFlow(UIEvent.ShowDialog(dialog))
            }
        }
    }

    private fun getCurrentMonth(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }

    private fun deleteBill(bill: BillModel) {
        Log.d("POTATO", "Remove View")
        viewModelScope.launch {
            deleteBillUseCase.execute(
                DeleteBillUseCase.Request(
                    Bill(
                        id = bill.id,
                        subType = bill.typeId,
                        description = bill.description,
                        value = bill.value,
                        month = 0,
                        recordDate = bill.date,
                        updateDate = ""
                    )
                )
            ).collect {
                val dialog = Dialog.DialogDefault(
                    title = stringResolve.getString(R.string.title_successful_process),
                    description = stringResolve.getString(R.string.description_delete_bill),
                    textSuccess = stringResolve.getString(R.string.copy_ok),
                    onSuccess = ::closeDialogDeletedBill,
                    onCancel = ::closeDialogDeletedBill
                )
                updateAndShowMessageRemoveBill()
            }
        }
    }

    private fun updateAndShowMessageRemoveBill() {
        viewModelScope.launch {
            loadData()
            Log.d("POTATO", "ShowMessage")
            eventsView.submitEventFlow(UIEvent.ShowSnackBar(stringResolve.getString(R.string.description_delete_bill)))
        }
    }

    private fun closeDialogDeletedBill() {
        viewModelScope.launch {
            //loadData()
            eventsView.hideEventFlow()
        }
    }

    private fun updateMonth(month: Int) {
        hideEventFlow()
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
        val text = bill.description
        val dialog = Dialog.DialogDefault(
            title = stringResolve.getString(R.string.title_detele_bill),
            description = stringResolve.getString(
                R.string.title_delete_description,
                text
            ),
            textSuccess = stringResolve.getString(R.string.copy_delete),
            textCancel = stringResolve.getString(R.string.copy_cancel),
            onSuccess = { deleteBill(bill) },
            onCancel = { hideEventFlow() }
        )
        //submitEventFlow()
        viewModelScope.launch {
            eventsView.submitEventFlow(UIEvent.ShowDialog(dialog))
        }

    }


    override fun handleAction(action: HomeContainerUiAction) {
        when (action) {
            is HomeContainerUiAction.Load -> loadData()
            is HomeContainerUiAction.DeleteBill -> showDeletionConfirmation(action.bill)
            is HomeContainerUiAction.UpdateMonth -> updateMonth(getCurrentMonth())
        }
    }

}
