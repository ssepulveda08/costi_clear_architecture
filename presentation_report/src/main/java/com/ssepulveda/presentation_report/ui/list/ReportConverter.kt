package com.ssepulveda.presentation_report.ui.list

import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.domain.useCase.GetReportMonthsUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class ReportConverter@Inject constructor() :
    CommonResultConverter<GetReportMonthsUseCase.Response, ReportModel>() {

    override fun convertSuccess(data: GetReportMonthsUseCase.Response): ReportModel {
        val defaultMonths = getDefaultMonths().map { month ->
            val monthInfo = data.list.firstOrNull { month.id == it.id }
            MonthData(
                month.id,
                month.name,
                monthInfo?.total ?: 0.0,
                monthInfo?.maxValue ?: 0.0,
                monthInfo?.minValue ?: 0.0,

            )
        }
        return ReportModel(
            defaultMonths
        )
    }

}