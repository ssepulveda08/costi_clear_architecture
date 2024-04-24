package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.ReportForMonth
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportForMonthDao {

    @Query("SELECT type.name AS name, SUM(bill.value) AS total\n" +
            "FROM TypeOfExpense AS type\n" +
            "JOIN SubType AS subType ON type.id = subType.type \n" +
            "JOIN BillEntity AS bill ON subType.id = bill.subType \n"+
            "WHERE bill.month = :month \n"+
            "GROUP BY type.name;")
    fun getReportForMonth(month: Int) : Flow<List<ReportForMonth>>
}
