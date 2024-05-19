package com.ssepulveda.presentation_home.home

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.domain.entity.CurrentWeekReport
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import com.ssepulveda.presentation_common.state.UiState
import com.ssepulveda.presentation_common.ui.generateColorRandom
import com.ssepulveda.presentation_common.ui.getCurrentDate
import com.ssepulveda.presentation_common.ui.getListColor
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.charts.Bar
import com.ssepulveda.presentation_home.home.ui.charts.CircleChart
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.random.Random

class HomerResultConverter @Inject constructor() :
    CommonResultConverter<GetHomeInformationUseCase.Response, HomeModel>() {

    override fun convertSuccess(data: GetHomeInformationUseCase.Response): HomeModel {
        val month = getDefaultMonths()[data.model.idMonth - 1]
        val response = data.model
        Log.d("POTATO", "RESPONSE $response")
        val bills = data.model.bills.map {
            BillModel(
                it.id ?: 0,
                it.value,
                it.description,
                it.subType,
                "",
                it.date
            )
        }

        val listColor = getListColor(data.model.dataReportType.size)
        val listForType = arrayListOf<CircleChart>()
        data.model.dataReportType.forEachIndexed { index, model ->
            listForType.add(
                CircleChart(
                    label = model.typeName.orEmpty(),
                    value = model.total?.toFloat() ?: 0f,
                    color = listColor[index]
                )
            )
        }
        return HomeModel(
            idMonth = response.idMonth,
            nameMonth = month.name,
            totalMonth = response.totalMonth,
            currentDate = Date().getCurrentDate(),
            bills = bills,
            maxValue = bills.maxOfOrNull { it.value } ?: 0.0,
            minValue = bills.minOfOrNull { it.value } ?: 0.0,
            isCurrentMonthHigher = response.idMonth < getCurrentMonth(),
            reportForType = listForType.toList(),
            reportForWeek = getDataChartWeed(data.model.dataReportWeed)
        )
    }
}
private fun getCurrentMonth(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.MONTH) + 1
}

private fun getDataChartWeed(dataReportWeed: List<CurrentWeekReport>): List<Bar> {
    val defaultList = getDefaultListWeed()
    dataReportWeed.forEach { data ->
        val index = if (data.dayOfWeek >= 1) data.dayOfWeek - 1 else data.dayOfWeek
        defaultList[index] = defaultList[index].copy(value = data.total.toFloat())
    }
    return defaultList.toList()
}

private fun getDefaultListWeed(): Array<Bar> {
    val listColor = getListColor(7)
    return arrayOf(
        Bar(
            value = 0f,
            color = listColor[0],
            label = R.string.copy_monday
        ), Bar(
            value = 0f,
            color = listColor[1],
            label =  R.string.copy_tuesday
        ), Bar(
            value = 0f,
            color = listColor[2],
            label = R.string.copy_wednesday
        ), Bar(
            value = 0f,
            color = listColor[3],
            label = R.string.copy_thursday
        ), Bar(
            value = 0f,
            color = listColor[4],
            label = R.string.copy_friday
        ), Bar(
            value = 0f,
            color = listColor[5],
            label = R.string.copy_saturday
        ), Bar(
            value = 0f,
            color = listColor[6],
            label = R.string.copy_sunday
        )
    )
}

