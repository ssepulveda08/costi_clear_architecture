package com.ssepulveda.costi.domain.useCase.config

import android.util.Log
import com.ssepulveda.costi.domain.entity.CostType
import com.ssepulveda.costi.domain.repository.AccountRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalCostTypeRepository
import com.ssepulveda.costi.domain.repository.LocalSubTypeRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SaveInitialConfigurationByDefaultUseCase(
    configuration: Configuration,
    private val localCostTypeRepository: LocalCostTypeRepository,
    private val localSubTypeRepository: LocalSubTypeRepository,
    private val accountRepository: AccountRepository,
    private val localConfigurationRepository: LocalConfigurationRepository,
) : UseCase<SaveInitialConfigurationByDefaultUseCase.Request, SaveInitialConfigurationByDefaultUseCase.Response>(
    configuration
) {

    data class Request(val types: List<CostType>) : UseCase.Request

    object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> =
        localConfigurationRepository.getMonthSet().map {
            Log.d("POTATO", "SaveInitialConfigurationByDefaultUseCase getMonthSet")
            request.types.forEach {
                val newCostType = localCostTypeRepository.addCostType(it)
                localSubTypeRepository.addSubTypes(it.subTypes ?: emptyList(), newCostType.id)
            }
           // Response
        }.flatMapConcat {
            Log.d("POTATO", "ACCOUNT lOAD DATA")
            accountRepository.loadDefaultData().map {
                Response
            }
        }
}

