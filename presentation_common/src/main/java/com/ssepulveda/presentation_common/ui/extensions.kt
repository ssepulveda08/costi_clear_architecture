package com.ssepulveda.presentation_common.ui

import androidx.compose.ui.graphics.Color
import java.lang.Exception
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Currency
import java.util.Currency.getInstance
import java.util.Date
import java.util.Locale
import kotlin.random.Random


fun getListColor(index: Int):List<Color> {
    val list = arrayListOf<Color>()
    for (i in 0..index) {
        list.add(addNewColor(list))
    }
    return list
}

private fun addNewColor(list: ArrayList<Color>): Color {
    val newColor = generateColorRandom()
    if (list.any { it == newColor }) {
        addNewColor(list)
    }
    return newColor
}

fun generateColorRandom(): Color {
    val minBrightness = 100 // brillo minimo
    var r = Random.nextInt(minBrightness, 256)
    var g = Random.nextInt(minBrightness, 256)
    var b = Random.nextInt(minBrightness, 256)

    val brightness = (r * 0.299 + g * 0.587 + b * 0.114).toInt() // calcular el total del brillo para saber si es un tono oscuro y ajustar el color
    while (brightness < minBrightness) {
        r = Random.nextInt(minBrightness, 256)
        g = Random.nextInt(minBrightness, 256)
        b = Random.nextInt(minBrightness, 256)
    }

    return Color(r, g, b)
}

fun Date.getCurrentDate(): String  {
    val formato = SimpleDateFormat("dd - MMM- yyyy", Locale.ENGLISH)
    return formato.format(this)
}

fun String.toCurrencyFormat(localeCode: String): String {
    val amount = this.toDoubleOrNull() ?: return this

    val formatter = DecimalFormat("#,###")
    formatter.currency = getInstance(localeCode)

    return formatter.currency?.currencyCode.orEmpty() + " " + formatter.format(amount)
}

fun Double.toCurrencyFormat(currencyCode: String): String {
    val formatter = DecimalFormat("#,###")
    return getCurrency(currencyCode)?.let {
         formatter.currency = it
         formatter.currency?.currencyCode.orEmpty() + " " + formatter.format(this)
    } ?: ""
}

/**
 *
 */
private fun getCurrency(currencyCode: String): Currency? = try {
    getInstance(currencyCode)
} catch (e: Exception) {
    null
}

fun Double?.orZero() = this ?: 0.0