package com.ssepulveda.presentation_add_bill.add

import com.ssepulveda.costi.domain.useCase.GetTypesAndSubTypesUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class TypeAndSubTypeConverter @Inject constructor(): CommonResultConverter<GetTypesAndSubTypesUseCase.Response, AddBillModel>() {
    override fun convertSuccess(data: GetTypesAndSubTypesUseCase.Response): AddBillModel {
        return AddBillModel(
            data.types.map {ItemDropdown(it.id, it.name)},
            data.subTypes.map {ItemDropdown(it.id ?: 0, it.name, it.type)}
        )
    }
}