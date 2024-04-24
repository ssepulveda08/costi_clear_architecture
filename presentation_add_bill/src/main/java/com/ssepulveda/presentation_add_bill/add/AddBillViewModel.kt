package com.ssepulveda.presentation_add_bill.add

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.useCase.GetTypesAndSubTypesUseCase
import com.ssepulveda.costi.domain.useCase.SaveBillUseCase
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
) : MviViewModel<AddBillModel, UiState<AddBillModel>, AddBillUiAction, AddBillSingleEvent>() {

    private val _types = MutableStateFlow<List<ItemDropdown>>(listOf())
    val types: StateFlow<List<ItemDropdown>> = _types.asStateFlow()

    private val _filterSubType = MutableStateFlow<List<ItemDropdown>>(listOf())
    val filterSubType: StateFlow<List<ItemDropdown>> = _filterSubType.asStateFlow()

    private val _subTypes = MutableStateFlow<List<ItemDropdown>>(listOf())

    private val _value = MutableStateFlow("")
    val value: StateFlow<String> = _value.asStateFlow()

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description.asStateFlow()

    private val _subTypeSelected = MutableStateFlow<ItemDropdown?>(null)
    val subTypeSelected: StateFlow<ItemDropdown?> = _subTypeSelected.asStateFlow()


    private val _enableButton = MutableStateFlow(false)
    val enableButton: StateFlow<Boolean> = _enableButton.asStateFlow()

    init {
        viewModelScope.launch {
            combine(value, description, subTypeSelected) { value1, value2, subtype ->
                value1.isNotEmpty() && value2.isNotEmpty() && subtype != null
            }.collect {
                _enableButton.value = it
            }
        }
    }

    private fun loadTypesAndSubTypes() {
        viewModelScope.launch {
            getTypesAndSubTypesUseCase.execute(GetTypesAndSubTypesUseCase.Request).map {
                converter.convert(it)
            }.collect {
                if (it is UiState.Success) {
                    _types.value = it.data.types
                    _subTypes.value = it.data.subTypes
                }
                submitState(it)
            }
        }
    }

    override fun initState(): UiState<AddBillModel> = UiState.Loading

    override fun handleAction(action: AddBillUiAction) {
        when (action) {
            is AddBillUiAction.LoadData -> loadTypesAndSubTypes()
            is AddBillUiAction.SelectedType -> {
                viewModelScope.launch {
                    val filter = _subTypes.value.filter { it.subType == action.type.id }
                    _subTypeSelected.value = null
                    _filterSubType.value = filter
                }
            }

            is AddBillUiAction.SelectedSubType -> {
                _subTypeSelected.value = action.subType
            }

            is AddBillUiAction.AddBill -> {
                viewModelScope.launch {
                    saveBillUseCase.execute(
                        SaveBillUseCase.Request(
                            Bill(
                                subType = _subTypeSelected.value?.id ?: 0,
                                description = _description.value,
                                value = _value.value.toDouble(),
                                month = 0
                            )
                        )
                    ).collect {
                        submitSingleEvent(AddBillSingleEvent.Close)
                    }
                }
            }
        }
    }

    fun setDescription(it: String) {
        _description.value = it
    }

    fun setValue(it: String) {
        _value.value = it
    }

}
