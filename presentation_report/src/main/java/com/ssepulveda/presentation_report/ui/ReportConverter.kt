package com.ssepulveda.presentation_report.ui

import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.domain.useCase.GetReportMonthsUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class ReportConverter@Inject constructor() :
    CommonResultConverter<GetReportMonthsUseCase.Response, ReportModel>() {

    override fun convertSuccess(data: GetReportMonthsUseCase.Response): ReportModel {
        val defaultMonths = getDefaultMonths().map { month ->
            MonthData(
                month.id,
                month.name,
                data.list.firstOrNull { month.id == it.id }?.total ?: 0.0
            )
        }
        return ReportModel(
            defaultMonths
        )
    }

}