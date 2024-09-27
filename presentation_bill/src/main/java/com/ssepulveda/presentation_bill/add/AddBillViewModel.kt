package com.ssepulveda.presentation_bill.add

import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.types.GetTypesAndSubTypesUseCase
import com.ssepulveda.costi.domain.useCase.bill.SaveBillUseCase
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBillViewModel @Inject constructor(
    private val getTypesAndSubTypesUseCase: GetTypesAndSubTypesUseCase,
    private val saveBillUseCase: SaveBillUseCase,
    private val converter: TypeAndSubTypeConverter
) : MviViewModel<FormInput, UiState<FormInput>, AddBillUiAction, AddBillSingleEvent>() {

    var accountId = 7777

    private fun loadTypesAndSubTypes() {
        viewModelScope.launch {
            getTypesAndSubTypesUseCase.execute(GetTypesAndSubTypesUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    private fun addBill(input: FormInput) {
        viewModelScope.launch {
            saveBillUseCase.execute(
                SaveBillUseCase.Request(
                    Bill(
                        subType = input.subTypeSelect?.id ?: 0,
                        description = input.description,
                        value = input.value.toDouble(),
                        month = 0,
                        recordDate = "",
                        updateDate = "",
                        accountId = accountId,
                    )
                )
            ).collect {
                when (it) {
                    is Result.Error -> {
                        submitState(UiState.Error(it.exception.localizedMessage.orEmpty()))
                    }

                    is Result.Success -> {
                        submitSingleEvent(AddBillSingleEvent.Close)
                    }
                }
            }
        }
    }

    override fun initState(): UiState<FormInput> = UiState.Loading

    override fun handleAction(action: AddBillUiAction) {
        when (action) {
            is AddBillUiAction.LoadData -> loadTypesAndSubTypes()

            is AddBillUiAction.AddBill -> {
                addBill(action.input)
            }
        }
    }


}
