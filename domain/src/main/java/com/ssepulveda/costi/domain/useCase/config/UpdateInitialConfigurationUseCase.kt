package com.ssepulveda.costi.domain.useCase.config

import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateInitialConfigurationUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
) : UseCase<UpdateInitialConfigurationUseCase.Request, UpdateInitialConfigurationUseCase.Response>(
    configuration
) {

    data class Request(val isSet: Boolean) : UseCase.Request

    object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> = flow {
        localConfigurationRepository.savedInitialConfiguration()
        this.emit(Response)
    }

}
