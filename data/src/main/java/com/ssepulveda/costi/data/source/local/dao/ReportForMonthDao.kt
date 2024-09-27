package com.ssepulveda.costi.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.ssepulveda.costi.data.source.local.entities.DayOfWeekReport
import com.ssepulveda.costi.data.source.local.entities.ReportForMonth
import com.ssepulveda.costi.data.source.local.entities.ReportForWeek
import com.ssepulveda.costi.data.source.local.entities.ReportTotalForType
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportForMonthDao {

    @Query("SELECT type.name AS name, SUM(bill.value) AS total\n" +
            "FROM TypeOfExpense AS type\n" +
            "JOIN SubType AS subType ON type.id = subType.type \n" +
            "JOIN BillEntity AS bill ON subType.id = bill.subType \n"+
            "WHERE bill.month = :month  AND bill.accountId = :accountId\n"+
            "GROUP BY type.name;")
    fun getReportForMonth(month: Int, accountId: Int) : Flow<List<ReportTotalForType>>

    @Query("SELECT strftime('%w', recordDate) AS dayOfWeek,\n" +
            "       strftime('%Y-%m-%d', recordDate) AS date,\n" +
            "       SUM(value) AS total\n" +
            "FROM BillEntity\n" +
            "WHERE recordDate BETWEEN date('now', 'weekday 0', '-6 days') AND date('now', 'weekday 0') AND month = :month AND accountId = :accountId \n" +
            "GROUP BY dayOfWeek\n" +
            "ORDER BY dayOfWeek")
    fun getReportForWeek(month: Int, accountId: Int) : Flow<List<ReportForWeek>>

    @Query("SELECT \n" +
            "    month,\n" +
            "    SUM(value) as total,\n" +
            "    MAX(value) as maxValue,\n" +
            "    MIN(value) as minValue \n" +
            "FROM \n" +
            "    BillEntity\n" +
            "GROUP BY \n" +
            "    month\n" +
            "ORDER BY \n" +
            "   month")
    fun getReportForMonth() : Flow<List<ReportForMonth>>

    @Query("SELECT SUM(value)  FROM BillEntity WHERE  month = :month")
    fun getTotalByMonth(month: Int): Flow<Double?>
    @Query("SELECT SUM(value)  FROM BillEntity WHERE  month = :month AND accountId = :accountId")
    fun getTotalByMonthAndAccount(month: Int, accountId: Int): Flow<Double?>

    @Query("WITH Week AS (\n" +
            "    SELECT\n" +
            "        strftime('%Y-%m', 'now') AS current_month,\n" +
            "        strftime('%W', recordDate) AS week,\n" +
            "        strftime('%d', recordDate) AS day,\n" +
            "        strftime('%w', recordDate) AS dayOf,"+

            "        value\n" +
            "    FROM BillEntity\n" +
            "    WHERE month = :month\n" +
            "),\n" +
            "Report AS (\n" +
            "    SELECT\n" +
            "        week,\n" +
            "        day,\n" +
            "        dayOf,\n" +
            "        COUNT(*) AS num_records,\n" +
            "        SUM(value) AS suma_values\n" +
            "    FROM Week\n" +
            "    GROUP BY week, day\n" +
            ")\n" +
            "SELECT\n" +
            "    week,\n" +
            "    day,\n" +
            "    dayOf,\n" +
            "    num_records AS numRecords,\n" +
            "    suma_values AS total\n" +
            "FROM Report\n" +
            "ORDER BY week, day;")
    fun getWeeksReportForMonth(month: Int) :Flow<List<DayOfWeekReport>>
}
