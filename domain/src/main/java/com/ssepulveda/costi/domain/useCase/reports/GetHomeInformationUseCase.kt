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

    object Request : UseCase.Request

    data class Response(val model: HomeScreen) : UseCase.Response

    @OptIn(FlowPreview::class)
    override fun process(request: Request): Flow<Response> =
        localConfigurationRepository.getMonthSet().flatMapConcat { idMonth ->
            localBillRepository.getAllBillsByMonth(idMonth).map {
                HomeScreen(
                    idMonth,
                    0.0,
                    it ?: listOf()
                )
            }.flatMapConcat { model ->
                localReportForMonthRepository.getReportForMonth(model.idMonth).map {
                    model.copy(dataReportType = it.reportForType, totalMonth = it.total ?: 0.0)
                }.flatMapConcat { modelEnd ->
                    localReportForMonthRepository.getCurrentWeekReport(modelEnd.idMonth).map {
                        Response(modelEnd.copy(dataReportWeed = it ?: listOf()))
                    }
                }
            }
        }

}