package com.ssepulveda.costi.domain.useCase.reports

import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllBillsByMonthUseCase(
    configuration: Configuration,
    private val localBillRepository: LocalBillRepository,
) : UseCase<GetAllBillsByMonthUseCase.Request, GetAllBillsByMonthUseCase.Response>(
    configuration
) {

    data class Request(val month: Int) : UseCase.Request

    data class Response(val types: List<Bill>) : UseCase.Response

    override fun process(request: Request): Flow<Response> {
        return localBillRepository.getAllBillsByMonth(request.month).map {
            Response(it ?: listOf())
        }
    }

}