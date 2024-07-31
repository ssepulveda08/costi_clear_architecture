package com.ssepulveda.presentation_bill.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDate(millis: Long): LocalDate {
        return Instant
            .ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToLocalDateWithFormatter(
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
    fun dateToString(date: LocalDate): String {
        Log.d("POTATO", "dateToString Start $date")
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
        val dateInMillis = convertMillisToLocalDateWithFormatter(date, dateFormatter)
        val response = dateFormatter.format(dateInMillis)
        Log.d("POTATO", "dateToString End $response")
        return response
    }

    const val DEFAULT_FORMATTER_DATE = "yyyy-MM-dd"

    @RequiresApi(Build.VERSION_CODES.O)
    fun LocalDate.toFormatterString(patter: String = DEFAULT_FORMATTER_DATE): String {
        val formatter = DateTimeFormatter.ofPattern(patter)
        return format(formatter)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertMillisToDate(millis: Long): String {
        /*Log.d("POTATO", "$millis")
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(Date(millis))*/
        val date = Date(millis)
        val formatter = SimpleDateFormat(
            DEFAULT_FORMATTER_DATE, Locale.getDefault()
        ).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }
        return formatter.format(date)
    }

    fun convertStringDateToMillis(dateString: String): Long? {
        Log.d("POTATO", "----dateString $dateString")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        Log.d("POTATO", "date: ${date.time} yy")
        return date?.time
    }

}