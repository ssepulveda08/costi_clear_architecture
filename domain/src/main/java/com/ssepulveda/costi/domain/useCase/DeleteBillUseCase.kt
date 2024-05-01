package com.ssepulveda.costi.domain.useCase

import android.util.Log
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteBillUseCase(
    configuration: Configuration,
    private val localBillRepository: LocalBillRepository
) : UseCase<DeleteBillUseCase.Request, DeleteBillUseCase.Response>(configuration) {

    class Request(val bill: Bill) : UseCase.Request

    data object Response : UseCase.Response

    override fun process(request: DeleteBillUseCase.Request): Flow<DeleteBillUseCase.Response> =
        flow {

            Log.d("POTATO", "DELETE BILL 2 ${request.bill.id}")
            localBillRepository.deleteBill(request.bill)
            emit(Response)
        }

}