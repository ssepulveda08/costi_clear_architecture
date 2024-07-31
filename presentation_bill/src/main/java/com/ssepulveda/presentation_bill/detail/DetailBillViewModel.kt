package com.ssepulveda.presentation_bill.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.UseCase
import com.ssepulveda.costi.domain.useCase.bill.GetBillByIdUseCase
import com.ssepulveda.costi.domain.useCase.bill.UpdateBillUseCase
import com.ssepulveda.costi.domain.useCase.types.GetTypesAndSubTypesUseCase
import com.ssepulveda.presentation_bill.add.ItemDropdown
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailBillViewModel @Inject constructor(
    private val getTypesAndSubTypesUseCase: GetTypesAndSubTypesUseCase,
    private val getBillByIdUseCase: GetBillByIdUseCase,
    private val updateBillUseCase: UpdateBillUseCase,
) : MviViewModel<FormInput, UiState<FormInput>, DetailBillUiAction, DetailBillSingleEvent>() {

    override fun initState(): UiState<FormInput> = UiState.Loading

    private fun loadTypesAndSubTypes(input: DetailInput) {
       // Log.d("POTATO", "${input.id}")
        viewModelScope.launch {
            getTypesAndSubTypesUseCase.execute(GetTypesAndSubTypesUseCase.Request)
                .collect { result ->
                    convertResult(result) {
                        val form = FormInput(
                            it.types.map { ItemDropdown(it.id, it.name) },
                            it.subTypes.map { ItemDropdown(it.id ?: 0, it.name, it.type) },
                            idBill = input.id.toInt(),
                        )
                        getBill(form)
                    }
                }
        }
    }

    private fun getBill(form: FormInput) {
        form.idBill?.let { idBill ->
            viewModelScope.launch {
                getBillByIdUseCase.execute(GetBillByIdUseCase.Request(idBill)).collect {
                    convertResult(it) { result ->
                        result.bill?.let { bill ->
                            val subTypeSelected =
                                form.subTypes.firstOrNull { sub -> sub.id == bill.subType }
                            val typeSelected = subTypeSelected?.let {
                                form.types.firstOrNull { type -> type.id == subTypeSelected.subType }
                            }
                            val newForm = form.copy(
                                value = bill.value.toString(),
                                description = bill.description,
                                //date = bill.date,
                                typeSelect = typeSelected,
                                subTypeSelect = subTypeSelected,
                                month = bill.month,
                                recordDate = bill.recordDate,
                                updateDate = bill.updateDate,
                            )
                            submitState(UiState.Success(newForm))
                        }
                    }
                }
            }
        }
    }

    private fun <T : UseCase.Response> convertResult(
        result: Result<T>,
        onSuccess: (T) -> Unit
    ) {
        when (result) {
            is Result.Error -> {
                UiState.Error(result.exception.localizedMessage.orEmpty())
            }

            is Result.Success -> {
                onSuccess(result.data)
            }
        }
    }

    private fun updateBill(input: FormInput) {
        viewModelScope.launch {
            submitState(UiState.Loading)
            val updateBill = Bill(
                id = input.idBill,
                subType = input.subTypeSelect?.id ?: 0,
                description = input.description,
                value = input.value.toDouble(),
                month = input.month ?: 0,
                recordDate = input.recordDate.orEmpty(),
                updateDate = input.updateDate.orEmpty()
            )
            updateBillUseCase.execute(UpdateBillUseCase.Request(updateBill)).collect {
                submitSingleEvent(DetailBillSingleEvent.Close)
            }
        }
    }


    override fun handleAction(action: DetailBillUiAction) {
        when (action) {
            is DetailBillUiAction.LoadData -> loadTypesAndSubTypes(action.input)
            is DetailBillUiAction.UpdateBill -> updateBill(action.bill)
        }
    }

}