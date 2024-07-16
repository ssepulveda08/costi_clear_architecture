package com.ssepulveda.costi.domain.useCase.bill

import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBillByIdUseCase(
    configuration: Configuration,
    private val localBillRepository: LocalBillRepository,
) : UseCase<GetBillByIdUseCase.Request, GetBillByIdUseCase.Response>(configuration) {

    data class Request(val billId: Int) : UseCase.Request

    data class Response(val bill: Bill?) : UseCase.Response

    override fun process(request: GetBillByIdUseCase.Request): Flow<Response> {
        return localBillRepository.getBillById(request.billId).map {
            Response(it)
        }
    }
}