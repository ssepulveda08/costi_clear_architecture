package com.ssepulveda.presentation_common.state

import android.util.Log
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.GetAllBillsByMonthUseCase

abstract class CommonResultConverter<T : Any, R : Any> {

    fun convert(
        result: Result<T>,
    ): UiState<R> {
        return when (result) {
            is Result.Error -> {
                UiState.Error(result.exception.localizedMessage.orEmpty())
            }
            is Result.Success -> {
                UiState.Success(convertSuccess(result.data))
            }
        }
    }

    abstract fun convertSuccess(data: T): R
}