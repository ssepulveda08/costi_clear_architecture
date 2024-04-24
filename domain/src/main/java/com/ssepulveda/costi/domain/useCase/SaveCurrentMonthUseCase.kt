package com.ssepulveda.costi.domain.useCase

import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveCurrentMonthUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
) : UseCase<SaveCurrentMonthUseCase.Request, SaveCurrentMonthUseCase.Response>(
    configuration
) {

    data class Request(val month: Int) : UseCase.Request

    object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> = flow{
        localConfigurationRepository.saveMonth(request.month)
        emit(Response)
    }

}