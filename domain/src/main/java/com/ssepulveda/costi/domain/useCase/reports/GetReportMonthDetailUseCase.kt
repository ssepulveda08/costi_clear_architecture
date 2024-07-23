package com.ssepulveda.costi.domain.useCase.reports


import com.ssepulveda.costi.domain.entity.ReportMonthDetail
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class GetReportMonthDetailUseCase(
    configuration: Configuration,
    private val localReportForMonthRepository: LocalReportForMonthRepository,
) : UseCase<GetReportMonthDetailUseCase.Request, GetReportMonthDetailUseCase.Response>(configuration) {

    data class Request(val monthId: Int) : UseCase.Request

    data class Response(val model: ReportMonthDetail?) : UseCase.Response

    override fun process(request: Request): Flow<Response> =
        localReportForMonthRepository.getReportMonthDetail(request.monthId).map {
            Response(it)
        }

}