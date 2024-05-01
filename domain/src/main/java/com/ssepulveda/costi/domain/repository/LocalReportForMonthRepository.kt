package com.ssepulveda.costi.domain.repository

import com.ssepulveda.costi.domain.entity.CurrentWeekReport
import com.ssepulveda.costi.domain.entity.ReportForMonth
import kotlinx.coroutines.flow.Flow

interface LocalReportForMonthRepository {
    fun getReportForMonth(month: Int) : Flow<ReportForMonth>
    fun getCurrentWeekReport(month: Int) : Flow<List<CurrentWeekReport>?>
}