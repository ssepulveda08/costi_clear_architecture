package com.ssepulveda.costi.data.repository.report

import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.data.source.local.entities.ReportTotalForType
import com.ssepulveda.costi.domain.entity.CurrentWeekReport
import com.ssepulveda.costi.domain.entity.ReportForMonth
import com.ssepulveda.costi.domain.entity.ReportMonth
import com.ssepulveda.costi.domain.entity.TotalValueByType
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class LocalReportForMonthRepositoryImpl(
    private val reportForMonthDao: ReportForMonthDao,
) : LocalReportForMonthRepository {

    override fun getReportForMonth(month: Int): Flow<ReportForMonth> =
        reportForMonthDao.getTotalByMonth(month).flatMapConcat { totalMonth ->
            reportForMonthDao.getReportForMonth(month).map { list ->
                ReportForMonth(
                    totalMonth ?: 0.0,
                    list.toReport()
                )
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

}

private fun List<ReportTotalForType>.toReport(): List<TotalValueByType> {
    return this.map { report ->
        TotalValueByType(
            report.name.orEmpty(),
            report.total ?: 0.0
        )
    }
}
