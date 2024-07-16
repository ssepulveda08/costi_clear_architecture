package com.ssepulveda.costi.domain.useCase.reports


import com.ssepulveda.costi.domain.entity.ReportMonth
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetReportMonthsUseCase(
    configuration: Configuration,
    private val localReportForMonthRepository: LocalReportForMonthRepository,
) : UseCase<GetReportMonthsUseCase.Request, GetReportMonthsUseCase.Response>(configuration) {

    object Request : UseCase.Request

    data class Response(val list: List<ReportMonth>) : UseCase.Response


    override fun process(request: Request): Flow<Response> =
        localReportForMonthRepository.getReportMonths().map {
            Response(it ?: emptyList())
        }

}