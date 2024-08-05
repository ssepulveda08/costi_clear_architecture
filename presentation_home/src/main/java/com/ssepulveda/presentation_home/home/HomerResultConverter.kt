package com.ssepulveda.presentation_home.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.domain.entity.CurrentWeekReport
import com.ssepulveda.costi.domain.entity.DayOfWeek
import com.ssepulveda.costi.domain.useCase.reports.GetHomeInformationUseCase
import com.ssepulveda.presentation_common.state.CommonResultConverter
import com.ssepulveda.presentation_common.ui.getCurrentDate
import com.ssepulveda.presentation_common.ui.getListColor
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.charts.Bar
import com.ssepulveda.presentation_home.home.ui.charts.CircleChart
import com.ssepulveda.presentation_home.home.utils.WeekInfo
import com.ssepulveda.presentation_home.home.utils.WeekUtils.getCurrentWeek
import com.ssepulveda.presentation_home.home.utils.WeekUtils.getWeeksForMonth
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class HomerResultConverter @Inject constructor() :
    CommonResultConverter<GetHomeInformationUseCase.Response, HomeModel>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun convertSuccess(data: GetHomeInformationUseCase.Response): HomeModel {
        val month = getDefaultMonths()[data.model.idMonth - 1]
        val response = data.model
        val bills = data.model.bills.map {
            BillModel(
                it.id ?: 0,
                it.value,
                it.description,
                it.subType,
                "",
                it.recordDate
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

        val weeksOfMonth = getWeeksForMonth(month.id)

        val weeks = weeksOfMonth.toListWeeklyReport(response.daysOfWeek)

        val currentWeek = if (weeksOfMonth.isNotEmpty()) {
            weeksOfMonth.indexOfFirst { it.numWeek == getCurrentWeek() }.takeIf { it >= 0 } ?: weeksOfMonth.last().numWeek
        } else {
            0
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
            reportForWeek = getDataChartWeed(data.model.dataReportWeed),
            currentWeek = currentWeek,
            weeklyReport = weeks
        )
    }
}

private fun List<WeekInfo>.toListWeeklyReport(daysOfWeek: List<DayOfWeek>): List<WeeklyReport> {
    //Log.d("POTATO", "$this, days $daysOfWeek")
    val mutableList = mutableListOf<WeeklyReport>()
    this.forEach { weekInfo ->
        val daysFilterForWeek = daysOfWeek.filter { weekInfo.numWeek == it.week }
        if (daysFilterForWeek.isNotEmpty()) {
            val arrayDais = getDefaultListWeed()
            daysFilterForWeek.forEach { day ->
                val index = day.dayOfWeek - 1
                arrayDais[index] = arrayDais[index].copy(value = day.total.toFloat())
            }
            mutableList.add(
                WeeklyReport(
                    weekInfo.numWeek,
                    weekInfo.startDate,
                    weekInfo.endDate,
                    arrayDais.toList()
                )
            )
        } else {
            mutableList.add(
                WeeklyReport(
                    weekInfo.numWeek,
                    weekInfo.startDate,
                    weekInfo.endDate,
                    getDefaultListWeed().toList()
                )
            )
        }
    }
    return mutableList
}

private fun getCurrentMonth(): Int {
    val calendar = Calendar.getInstance()
    return calendar.get(Calendar.MONTH) + 1
}

private fun getDataChartWeed(dataReportWeed: List<CurrentWeekReport>): List<Bar> {
    val defaultList = getDefaultListWeed()
    dataReportWeed.forEach { data ->
        val index = if (data.dayOfWeek >= 1) data.dayOfWeek - 1 else 6
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
            label = R.string.copy_tuesday
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

