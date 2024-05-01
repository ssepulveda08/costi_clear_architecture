package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.ReportForWeek
import com.ssepulveda.costi.data.source.local.entities.ReportTotalForType
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportForMonthDao {

    @Query("SELECT type.name AS name, SUM(bill.value) AS total\n" +
            "FROM TypeOfExpense AS type\n" +
            "JOIN SubType AS subType ON type.id = subType.type \n" +
            "JOIN BillEntity AS bill ON subType.id = bill.subType \n"+
            "WHERE bill.month = :month \n"+
            "GROUP BY type.name;")
    fun getReportForMonth(month: Int) : Flow<List<ReportTotalForType>>

    @Query("SELECT strftime('%w', recordDate) AS dayOfWeek,\n" +
            "       strftime('%Y-%m-%d', recordDate) AS date,\n" +
            "       SUM(value) AS total\n" +
            "FROM BillEntity\n" +
            "WHERE recordDate BETWEEN date('now', 'weekday 0', '-6 days') AND date('now', 'weekday 0') AND month = :month \n" +
            "GROUP BY dayOfWeek\n" +
            "ORDER BY dayOfWeek")
    fun getReportForWeek(month: Int) : Flow<List<ReportForWeek>>

    @Query("SELECT SUM(value)  FROM BillEntity WHERE  month = :month")
    fun getTotalByMonth(month: Int): Flow<Double?>
}
