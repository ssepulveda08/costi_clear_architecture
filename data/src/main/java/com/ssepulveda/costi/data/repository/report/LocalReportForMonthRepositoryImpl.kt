package com.ssepulveda.costi.data.repository.report

import com.ssepulveda.costi.data.source.getDefaultMonths
import com.ssepulveda.costi.data.source.local.dao.BillEntityDao
import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.data.source.local.entities.BillAndInformation
import com.ssepulveda.costi.data.source.local.entities.BillEntity
import com.ssepulveda.costi.data.source.local.entities.DayOfWeekReport
import com.ssepulveda.costi.data.source.local.entities.ReportTotalForType
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.costi.domain.entity.CurrentWeekReport
import com.ssepulveda.costi.domain.entity.DayOfWeek
import com.ssepulveda.costi.domain.entity.ReportForMonth
import com.ssepulveda.costi.domain.entity.ReportMonth
import com.ssepulveda.costi.domain.entity.ReportMonthDetail
import com.ssepulveda.costi.domain.entity.TotalValueByType
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocalReportForMonthRepositoryImpl(
    private val reportForMonthDao: ReportForMonthDao,
    private val billEntityDao: BillEntityDao
) : LocalReportForMonthRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getReportForMonth(month: Int): Flow<ReportForMonth> =
        reportForMonthDao.getTotalByMonth(month).flatMapConcat { totalMonth ->
            reportForMonthDao.getReportForMonth(month).flatMapConcat { list ->
                 reportForMonthDao.getWeeksReportForMonth(month).map { days ->
                     ReportForMonth(
                         total = totalMonth ?: 0.0,
                         reportForType = list.toReport(),
                         daysOfWeek = days.map { it.toDayOfWeek() }
                     )
                 }
            }
        }

    override fun getCurrentWeekReport(month: Int): Flow<List<CurrentWeekReport>> =
        reportForMonthDao.getTotalByMonth(month).flatMapConcat { totalMonth ->
            reportForMonthDao.getReportForWeek(month).map {
                it.map { week ->
                    CurrentWeekReport(
                        week.dayOfWeek ?: 0,
                        week.data ?: "",
                        week.total ?: 0.0
                    )
                }
            }
        }

    override fun getReportMonths(): Flow<List<ReportMonth>> =
        reportForMonthDao.getReportForMonth().map { list ->
            list.map { ReportMonth(it.month, it.total, it.maxValue, it.minValue) }
        }

    override fun getReportMonthDetail(month: Int): Flow<ReportMonthDetail?> = flow {
        getDefaultMonths().firstOrNull { it.id == month }?.let { model ->
            billEntityDao.getAllByMonth(month).collect {list ->
                val response = ReportMonthDetail(
                    model,
                    list?.map { it.toBill() }
                )
                emit(response)
            }
        } ?: emit(null)
    }
}

fun DayOfWeekReport.toDayOfWeek(): DayOfWeek = DayOfWeek(
    week = this.week,
    day = this.day,
    dayOfWeek = this.dayOf,
    numRecords = this.numRecords,
    total = this.total,
)

fun BillAndInformation.toBill(): Bill = Bill(
    id = this.id,
    subType = this.subType,
    description = this.description,
    value = this.value,
    month = this.month,
    recordDate = this.recordDate,
    updateDate = this.updateDate
)


private fun List<ReportTotalForType>.toReport(): List<TotalValueByType> {
    return this.map { report ->
        TotalValueByType(
            report.name.orEmpty(),
            report.total ?: 0.0
        )
    }
}
