package com.ssepulveda.costi.domain.useCase

import android.util.Log
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SaveBillUseCase(
    configuration: Configuration,
    private val localBillRepository: LocalBillRepository,
    private val localConfigurationRepository: LocalConfigurationRepository,
) : UseCase<SaveBillUseCase.Request, SaveBillUseCase.Response>(configuration) {

    data class Request(val bill: Bill) : UseCase.Request

    data class Response(val id: Int) : UseCase.Response

    override fun process(request: Request): Flow<Response> = localConfigurationRepository.getMonthSet().flatMapConcat {
            localBillRepository.addBill(
                request.bill.copy(month = it)
            ).map {id ->
                Response(id.toInt())
            }
        }


}