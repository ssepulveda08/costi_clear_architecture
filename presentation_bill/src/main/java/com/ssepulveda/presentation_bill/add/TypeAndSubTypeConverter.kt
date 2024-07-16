package com.ssepulveda.presentation_bill.add

import com.ssepulveda.costi.domain.useCase.types.GetTypesAndSubTypesUseCase
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class TypeAndSubTypeConverter @Inject constructor(): CommonResultConverter<GetTypesAndSubTypesUseCase.Response, FormInput>() {
    override fun convertSuccess(data: GetTypesAndSubTypesUseCase.Response): FormInput {
        return FormInput(
            data.types.map {ItemDropdown(it.id, it.name)},
            data.subTypes.map {ItemDropdown(it.id ?: 0, it.name, it.type)}
        )
    }
}