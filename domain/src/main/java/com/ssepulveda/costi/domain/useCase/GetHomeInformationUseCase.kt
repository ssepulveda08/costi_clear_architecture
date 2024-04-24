package com.ssepulveda.costi.domain.useCase

import android.util.Log
import com.ssepulveda.costi.domain.entity.HomeScreen
import com.ssepulveda.costi.domain.repository.LocalBillRepository
import com.ssepulveda.costi.domain.repository.LocalConfigurationRepository
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
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
            Log.d("POTATO", "1: $idMonth")
            localBillRepository.getAllBillsByMonth(idMonth).map {
                Log.d("POTATO", "2: $it")
                    HomeScreen(
                        idMonth,
                        it
                    )
            }.flatMapConcat { model ->
                Log.d("POTATO", "3: $model")
                localReportForMonthRepository.getReportForMonth(model.idMonth).map {
                    Log.d("POTATO", "4: $it")
                    Response(
                        model.copy(dataReport = it)
                    )
                }
            }
        }

}