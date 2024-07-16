package com.ssepulveda.costi.domain.useCase.types

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCostTypeUseCase(
    configuration: Configuration,
    private val localCostTypeRepository: LocalCostTypeRepository,
) : UseCase<GetCostTypeUseCase.Request, GetCostTypeUseCase.Response>(configuration){

    object Request : UseCase.Request

    data class Response(val types: List<CostType>) : UseCase.Response

    override fun process(request: Request): Flow<Response> = localCostTypeRepository.getAllCostType().map {
        Response(it)
    }
}