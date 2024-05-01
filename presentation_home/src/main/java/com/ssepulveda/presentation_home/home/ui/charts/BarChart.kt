package com.ssepulveda.presentation_home.home.ui.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val PADDING_CHART = 5
private const val PADDING_VERTICAL_AND_HORIZONTAL = 100
private const val PADDING_VERTICAL = 50f

@Composable
fun BarChart(
    list: List<Bar>,
    title: @Composable ColumnScope.() -> Unit = {},
    background: Color = Color.Transparent,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = Modifier.then(modifier),
        shape = RoundedCornerShape(8.dp),
        color = background,
        tonalElevation = 4.dp
    ) {

        val textMeasurer = rememberTextMeasurer()
        Column {
            val animationProgress = remember { Animatable(0f) }
            LaunchedEffect(list) {
                animationProgress.animateTo(1f, animationSpec = tween(durationMillis = 2000))
            }
            this.title()
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .padding(16.dp)
            ) {

                val maxDataValue = list.maxByOrNull { it.value }?.value ?: 0f
                val unitHeight = (size.height / maxDataValue)


                // Draw Eje vertical
                drawLine(
                    start = Offset(x = PADDING_VERTICAL, y = 0f),
                    end = Offset(x = PADDING_VERTICAL, y = size.height - PADDING_CHART),
                    color = Color.Red,
                    strokeWidth = 5f
                )


                //Draw Label vertical
                val valorUniLabelV = (maxDataValue / 5).toInt()
                val spacer = (size.height - PADDING_CHART) / 5
                for (i in 0..5) {
                    //Log.d("POTATO", "Value Unit $valorUniLabelV")
                    val labelValue = if (i == 0) 0 else valorUniLabelV * i
                    val textToDraw = labelValue.converterText()

                    val style = TextStyle(
                        fontSize = 8.sp,
                        color = Color.Black,
                    )
                    val newValue = textMeasurer.measure(textToDraw, style)
                    drawText(
                        newValue,
                        topLeft = Offset(
                            x = 0f,
                            y = if (i == 0) size.height - 24 else (size.height - PADDING_CHART) - (i * spacer)
                        )
                    )

                }

                // Draw Eje Horizontal
                drawLine(
                    start = Offset(x = 48f, y = size.height - PADDING_CHART),
                    end = Offset(x = size.width, y = size.height - PADDING_CHART),
                    color = Color.Red,
                    strokeWidth = 5f
                )

                // val unitHeight = (size.height / maxDataValue)
                val distanceInX = (size.width - PADDING_VERTICAL_AND_HORIZONTAL) / (list.size)


                // Paint bars on the graph
                list.forEachIndexed { index, bar ->
                    val endPointInX = distanceInX * (index + 1) + PADDING_VERTICAL
                    val barHeight = unitHeight * bar.value * animationProgress.value //- PADDING_CHART
                    val barWidth = distanceInX / 2
                    val startY = size.height - barHeight - PADDING_CHART
                    val startX = endPointInX - barWidth

                    //Log.d("POTATO", "SIZE $index - ${size.height} - x $startX - Y $startY - Barheight $barHeight - unitHeight $unitHeight value - ${bar.value}")
                    if (bar.value > 0) {
                        drawRect(
                            color = bar.color ?: Color.Gray,
                            topLeft = Offset(startX, startY),
                            size = Size(barWidth, barHeight),
                            //style = Stroke(width = 1.dp.toPx()),
                        )
                    }

                    val styleLabel = TextStyle(
                        fontSize = 8.sp,
                        color = Color.Black,
                    )
                    val newValue = textMeasurer.measure(bar.label, styleLabel)
                    drawText(
                        newValue,
                        topLeft = Offset(
                            x = startX,
                            y = size.height - 8
                        )
                    )
                }
            }
        }
    }
}

private fun Int.converterText(): String {
    return when {
        this == 0 -> {
            "$ 0"
        }
        this >= 1000000 -> {
            "$ ${(this / 1000000)}m"
        }
        this >= 1000 -> {
            "$ ${(this / 1000)}k"
        } else -> "$this"
    }
}

data class Bar(
    val label: String,
    val value: Float,
    val color: Color? = null
)