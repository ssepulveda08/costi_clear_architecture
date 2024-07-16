package com.ssepulveda.costi.domain.useCase.bill

import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateBillUseCase(
    configuration: Configuration,
    private val localBillRepository: LocalBillRepository,
): UseCase<UpdateBillUseCase.Request, UpdateBillUseCase.Response>(configuration) {

    data class Request(val bill: Bill) : UseCase.Request

    object Response : UseCase.Response

    override fun process(request: Request): Flow<Response> = flow {
        localBillRepository.updateBill(request.bill)
        emit(Response)
    }
}