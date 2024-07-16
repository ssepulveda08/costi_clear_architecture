package com.ssepulveda.costi.domain.useCase.config

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveInitialConfigurationByDefaultUseCase(
    configuration: Configuration,
    private val localCostTypeRepository: LocalCostTypeRepository,
    private val localSubTypeRepository: LocalSubTypeRepository,
) : UseCase<SaveInitialConfigurationByDefaultUseCase.Request, SaveInitialConfigurationByDefaultUseCase.Response>(configuration){

    data class Request(val types: List<CostType>) : UseCase.Request

    object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> {
        return flow {
            request.types.forEach {
                val newCostType = localCostTypeRepository.addCostType(it)
                localSubTypeRepository.addSubTypes(it.subTypes ?: emptyList() , newCostType.id)
            }
            emit(Response)
        }
    }
}

