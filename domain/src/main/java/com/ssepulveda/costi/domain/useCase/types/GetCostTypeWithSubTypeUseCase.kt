package com.ssepulveda.costi.domain.useCase.types

import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single

class GetCostTypeWithSubTypeUseCase(
    configuration: Configuration,
    private val localCostTypeRepository: LocalCostTypeRepository,
    private val localSubTypeRepository: LocalSubTypeRepository,
) : UseCase<GetCostTypeWithSubTypeUseCase.Request, GetCostTypeWithSubTypeUseCase. Response>(configuration){

    object Request : UseCase.Request

    data class Response(val types: List<CostType>) : UseCase.Response

    override fun process(request: Request): Flow<Response> {

        return flow {
            val types = localCostTypeRepository.getAllCostType().single()
            val newTypes = types.toTypedArray()
            types.forEachIndexed { index, costType ->
                val subTypes = localSubTypeRepository.getAllSubTypeByType(costType).single()
                newTypes[index] = costType.copy(subTypes = subTypes)
            }
            emit(Response(newTypes.toList()))
        }
    }
}
