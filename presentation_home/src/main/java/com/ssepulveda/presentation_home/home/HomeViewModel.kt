package com.ssepulveda.presentation_home.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Account
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.account.AddAccountUseCase
import com.ssepulveda.costi.domain.useCase.account.GetAccountsByMonthUseCase
import com.ssepulveda.costi.domain.useCase.config.SaveCurrentMonthUseCase
import com.ssepulveda.modal_dialogs.entities.ActionDialog
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.presentation_common.di.EventsView
import com.ssepulveda.presentation_common.inputs.AddBillInput
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UIEvent
import com.ssepulveda.presentation_common.state.UiState
import com.ssepulveda.presentation_common.ui.StringResolve
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.account.AccountModel
import com.ssepulveda.presentation_home.home.ui.account.TabsAccount
import com.ssepulveda.presentation_home.home.ui.homeContainer.HomeSingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAccountsByMonthUseCase: GetAccountsByMonthUseCase,
    private val saveCurrentMonthUseCase: SaveCurrentMonthUseCase,
    private val stringResolve: StringResolve,
    private val eventsView: EventsView,
    private val addAccountUseCase: AddAccountUseCase,
) : MviViewModel<AccountModel, UiState<AccountModel>, HomeUiAction, HomeSingleEvent>() {

    private val _tabs: MutableStateFlow<List<TabsAccount>> = MutableStateFlow(listOf())
    val tabs: StateFlow<List<TabsAccount>> = _tabs.asStateFlow()

    override fun initState(): UiState<AccountModel> = UiState.Loading

    init {
        viewModelScope.launch {
            eventsView.uiEvent.collectLatest { event ->
                event?.let {
                    submitEventFlow(it)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            getAccountsByMonthUseCase.execute(GetAccountsByMonthUseCase.Request).collect {
                val result = when (it) {
                    is Result.Error -> {
                        UiState.Error(it.exception.localizedMessage.orEmpty())
                    }

                    is Result.Success -> {
                        val data = it.data.list.map { account ->
                            TabsAccount(
                                id = account.id ?: 7777,
                                name = account.name,
                                selected = (account.id ?: 0) < 12,
                                capped = account.capped
                            )
                        }
                        loadTabs(data)
                        UiState.Success(AccountModel(true))
                    }
                }

                submitState(result)
            }
        }
    }

    private fun loadTabs(data: List<TabsAccount>) {
        _tabs.value = data
    }

    /* private fun validateCurrentMonth(uiState: UiState<HomeModel>) { TODO MOVE USE CASE
         if (uiState is UiState.Success && uiState.data.isCurrentMonthHigher) {
             val dialog = Dialog.DialogDefault(
                 stringResolve.getString(R.string.title_update_month),
                 stringResolve.getString(R.string.description_update_month),
                 textCancel = stringResolve.getString(R.string.copy_close),
                 textSuccess = stringResolve.getString(R.string.copy_continue),
                 onCancel = { hideEventFlow() },
                 onSuccess = { updateMonth(getCurrentMonth()) }
             )
             submitEventFlow(UIEvent.ShowDialog(dialog))
         }
     } */

    private fun getCurrentMonth(): Int {
        // TODO MOVE A UTILS
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.MONTH) + 1
    }


    /* private fun updateAndShowMessageRemoveBill() {
         viewModelScope.launch {
             loadData()
             Log.d("POTATO", "ShowMessage")
             submitEventFlow(UIEvent.ShowSnackBar(stringResolve.getString(R.string.description_delete_bill)))
         }
     }

     private fun closeDialogDeletedBill() {
         hideEventFlow()
         loadData()
     } */

    private fun updateMonth(month: Int) {
        hideEventFlow()
        submitState(UiState.Loading)
        viewModelScope.launch {
            saveCurrentMonthUseCase.execute(SaveCurrentMonthUseCase.Request(month))
                .collect {
                    delay(500)
                    loadData()
                }
        }
    }

    /*  private fun showDeletionConfirmation(bill: BillModel) {
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
          submitEventFlow(UIEvent.ShowDialog(dialog))
      } */

    fun openAddBill() {
        val idSelectTab = _tabs.value.firstOrNull { it.selected }?.id ?: 7777
        submitSingleEvent(
            HomeSingleEvent.OpenAddBill(
                NavRoutes.AddBill.routeForAccount(
                    AddBillInput(idSelectTab)
                )
            )
        )
    }

    private fun showDialogCloseMonth() {
        val dialog = Dialog.DialogDefault(
            title = stringResolve.getString(R.string.title_close_month),
            description = stringResolve.getString(R.string.description_close_month),
            textCancel = stringResolve.getString(R.string.copy_cancel),
            textSuccess = stringResolve.getString(R.string.copy_continue),
            onCancel = { hideEventFlow() },
            onSuccess = { updateMonth(getCurrentMonth() + 1) }
        )
        submitEventFlow(UIEvent.ShowDialog(dialog))
    }

    private fun showDialogAddAccount() {
        submitEventFlow(UIEvent.ShowDialog(Dialog.DialogRegisterAccount(
            onCancel = ::hideEventFlow,
            onSuccess = {
                addAccount(it)
            }
        )))
    }

    private fun addAccount(action: ActionDialog) {
        hideEventFlow()
        if (action is ActionDialog.AddAccount) {
            viewModelScope.launch {
                val account = Account(
                    month = 0,
                    name = action.data.name,
                    capped = action.data.capped,
                )
                addAccountUseCase.execute(AddAccountUseCase.Request(account)).collect {
                    Log.d("POTATO", "Result $it")
                }
            }
        }
    }


    override fun handleAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.Load -> loadData()
            HomeUiAction.OpenDialogAddAccount -> showDialogAddAccount()
            HomeUiAction.UpdateMonth -> TODO()
            HomeUiAction.OpenDialogCloseMonth -> showDialogCloseMonth()
            is HomeUiAction.SelectTab -> {
                val newValue = _tabs.value.map {
                    it.copy(selected = it.id == action.tab.id)
                }
                _tabs.value = newValue
            }
        }
    }

}