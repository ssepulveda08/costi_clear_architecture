package com.ssepulveda.costi.data.repository.report

import com.ssepulveda.costi.data.source.local.dao.ReportForMonthDao
import com.ssepulveda.costi.domain.entity.ReportForMonth
import com.ssepulveda.costi.domain.repository.LocalReportForMonthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalReportForMonthRepositoryImpl(
    private val reportForMonthDao: ReportForMonthDao,
) : LocalReportForMonthRepository {

    override fun getReportForMonth(month: Int): Flow<List<ReportForMonth>> =
        reportForMonthDao.getReportForMonth(month).map {
            it.map {report ->
                ReportForMonth(
                    report.name ?: "",
                    report.total ?: 0.0
                )
            }
        }

}