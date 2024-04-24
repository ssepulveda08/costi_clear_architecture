package com.ssepulveda.costi.domain.useCase

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.entity.SubTypeCost
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetTypesAndSubTypesUseCase(
    configuration: Configuration,
    private val localSubTypeRepository: LocalSubTypeRepository,
    private val localCostTypeRepository: LocalCostTypeRepository,
) : UseCase<GetTypesAndSubTypesUseCase.Request, GetTypesAndSubTypesUseCase.Response>(configuration) {


    data object Request : UseCase.Request

    data class Response(val types: List<CostType>, val subTypes: List<SubTypeCost> ) : UseCase.Response

    @OptIn(FlowPreview::class)
    override fun process(request: Request): Flow<Response> {
        return localCostTypeRepository.getAllCostType().flatMapConcat { types ->
            localSubTypeRepository.getAllSubType().map {
                Response(types, it)
            }
        }
    }

}