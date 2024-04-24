package com.ssepulveda.presentation_home.home

import com.github.tehras.charts.line.LineChartData
import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.domain.useCase.GetCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class HomerResultConverter @Inject constructor() :
    CommonResultConverter<GetHomeInformationUseCase.Response, HomeModel>() {
    override fun convertSuccess(data: GetHomeInformationUseCase.Response): HomeModel {
        val month = getDefaultMonths()[data.model.idMonth - 1]
        val bills = data.model.bills.map { BillModel(
            it.id ?: 0,
            it.value,
            it.description,
            it.subType,
            "",
        ) }
        val point = data.model.dataReport.map { LineChartData.Point(
            label = it.typeName,
            value = it.total.toFloat()
        ) }
        return HomeModel(
            nameMoth = month.name,
            bills,
            point
        )
    }
}