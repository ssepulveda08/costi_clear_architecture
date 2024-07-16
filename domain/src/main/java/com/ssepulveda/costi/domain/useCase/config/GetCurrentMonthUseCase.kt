package com.ssepulveda.costi.domain.useCase.config

import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCurrentMonthUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
) : UseCase<GetCurrentMonthUseCase.Request, GetCurrentMonthUseCase.Response>(
    configuration
) {

    object Request : UseCase.Request

    data class Response(val month: Int) : UseCase.Response

    override fun process(request: Request): Flow<Response> =
        localConfigurationRepository.getMonthSet().map {
            Response(it)
        }

}