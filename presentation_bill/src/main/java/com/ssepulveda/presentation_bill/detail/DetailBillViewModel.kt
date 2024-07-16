package com.ssepulveda.presentation_bill.detail

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Result
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
) : MviViewModel<FormInput, UiState<FormInput>, DetailBillUiAction, DetailBillSingleEvent>() {

    override fun initState(): UiState<FormInput> = UiState.Loading

    private fun loadTypesAndSubTypes(input: DetailInput) {
        Log.d("POTATO", "${input.id}")
        viewModelScope.launch {
            getTypesAndSubTypesUseCase.execute(GetTypesAndSubTypesUseCase.Request).collect { result ->
                when (result) {
                    is Result.Error -> {
                        UiState.Error(result.exception.localizedMessage.orEmpty())
                    }
                    is Result.Success -> {
                        val state = UiState.Success(FormInput(
                            result.data.types.map { ItemDropdown(it.id, it.name) },
                            result.data.subTypes.map { ItemDropdown(it.id ?: 0, it.name, it.type) }
                        ))
                        submitState(state)
                    }
                }
            }
        }
    }

    override fun handleAction(action: DetailBillUiAction) {
         when(action) {
            is DetailBillUiAction.LoadData -> loadTypesAndSubTypes(action.input)
         }
    }

}