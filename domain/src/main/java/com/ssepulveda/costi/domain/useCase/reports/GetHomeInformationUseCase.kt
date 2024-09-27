package com.ssepulveda.costi.domain.useCase.reports

import com.ssepulveda.costi.domain.entity.HomeScreen
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import com.ssepulveda.costi.domain.useCase.UseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetHomeInformationUseCase(
    configuration: Configuration,
    private val localConfigurationRepository: LocalConfigurationRepository,
    private val localBillRepository: LocalBillRepository,
    private val localReportForMonthRepository: LocalReportForMonthRepository,
) : UseCase<GetHomeInformationUseCase.Request, GetHomeInformationUseCase.Response>(configuration) {

    data class Request(val accountId: Int) : UseCase.Request

    data class Response(val model: HomeScreen) : UseCase.Response

    @OptIn(FlowPreview::class)
    override fun process(request: Request): Flow<Response> =
        localConfigurationRepository.getMonthSet().flatMapConcat { idMonth ->
            localBillRepository.getAllBillsByMonth(idMonth, request.accountId).map {
                HomeScreen(
                    idMonth,
                    0.0,
                    it ?: listOf()
                )
            }.flatMapConcat { model ->
                localReportForMonthRepository.getReportForMonth(model.idMonth, request.accountId).map {
                    model.copy(
                        dataReportType = it.reportForType,
                        totalMonth = it.total ?: 0.0,
                        daysOfWeek = it.daysOfWeek
                    )
                }.flatMapConcat { modelEnd ->
                    localReportForMonthRepository.getCurrentWeekReport(modelEnd.idMonth, request.accountId).map {
                        Response(modelEnd.copy(dataReportWeed = it ?: listOf()))
                    }
                }
            }
        }

}