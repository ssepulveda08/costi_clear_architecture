package com.ssepulveda.costi.domain.useCase.config

import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetInitialConfigurationUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
): UseCase<GetInitialConfigurationUseCase.Request, GetInitialConfigurationUseCase.Response>(configuration){


    object  Request : UseCase.Request

    data class Response(val isSet: Boolean) : UseCase.Response

    override fun process(request: Request): Flow<Response> = localConfigurationRepository.hasInitialConfiguration().map {
        Response(it)
    }

}
