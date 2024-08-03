package com.ssepulveda.presentation_home.home.utils

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.IsoFields
import java.time.temporal.TemporalAdjusters
import java.util.Calendar.WEEK_OF_MONTH
import java.util.Locale

object WeekUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeeksForMonth(month: Int): List<WeekInfo> {
        if (month > 12 || month <= 0) {
            return listOf()
        }
        val today = LocalDate.now()
        val year = today.year

        val weeks = mutableListOf<WeekInfo>()

        val firstDay = LocalDate.of(year, month, 1)
        val endDay = firstDay.withDayOfMonth(firstDay.lengthOfMonth())

        val firstWeek = firstDay.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
        val endWeek = endDay.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)

        for (i in firstWeek..endWeek) {
            val dates = getStartAndEndDatesOfWeek(year, i)
            weeks.add(
                WeekInfo(
                    i,
                    dates.first,
                    dates.second
                )
            )

        }
        return weeks
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentWeek(): Int {
        val nowDate = LocalDate.now()
        return nowDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getStartAndEndDatesOfWeek(year: Int, weekOfYear: Int): Pair<String, String> {

        // Crear la fecha del primer día del año
        val firstDayOfYear = LocalDate.of(year, 1, 1)

        // Encontrar la fecha correspondiente al primer día de la semana en el año
        val startOfWeek =
            firstDayOfYear.with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfYear.toLong())
                .with(IsoFields.WEEK_BASED_YEAR, year.toLong())
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

        // Encontrar la fecha del final de la semana
        val endOfWeek = startOfWeek.plusDays(6)

        val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

        return Pair(startOfWeek.format(dateFormat), endOfWeek.format(dateFormat))
    }
}


@RequiresApi(Build.VERSION_CODES.O)
private fun convertMillisToLocalDateWithFormatter(
    date: LocalDate,
    dateTimeFormatter: DateTimeFormatter
): LocalDate {

    val dateInMillis = LocalDate.parse(date.format(dateTimeFormatter), dateTimeFormatter)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()


    return Instant
        .ofEpochMilli(dateInMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toChangeFormatter(patter: String = "EEE d MMMM"): String {

    // Definir el formato de entrada
    val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    // Parsear la fecha del string
    val fecha = LocalDate.parse(this, formatoEntrada)

    // Crear un formato de salida para "21 Agosto"
    val formatoSalida = DateTimeFormatterBuilder()
        .appendPattern(patter)
        .toFormatter()

    // Convertir la fecha al formato deseado
    return fecha.format(formatoSalida)

}


data class WeekInfo(
    val numWeek: Int,
    val startDate: String,
    val endDate: String
)

