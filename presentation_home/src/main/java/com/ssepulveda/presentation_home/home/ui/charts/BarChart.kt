package com.ssepulveda.presentation_home.home.ui.charts

import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssepulveda.presentation_home.R

private const val PADDING_CHART = 5
private const val PADDING_VERTICAL_AND_HORIZONTAL = 100
private const val PADDING_VERTICAL = 75f

@Composable
fun BarChart(
    list: List<Bar>,
    modifier: Modifier = Modifier,
    title: @Composable ColumnScope.() -> Unit = {},
) {
    val context = LocalContext.current
    val textMeasurer = rememberTextMeasurer()
    val newStyleLabel = MaterialTheme.typography.labelSmall.copy(
        fontSize = 8.sp,
        color = if(isSystemInDarkTheme())  Color.White else Color.Black
    )
    Column(
        modifier = Modifier.then(modifier)
    ) {
        val animationProgress = remember { Animatable(0f) }
        LaunchedEffect(list) {
            animationProgress.animateTo(1f, animationSpec = tween(durationMillis = 2000))
        }
        this.title()
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp, top = 4.dp)
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

                val newValue = textMeasurer.measure(textToDraw, newStyleLabel)
                drawText(
                    newValue,
                    topLeft = Offset(
                        x = 0f,
                        y = if (i == 0) size.height - 23 else (size.height - PADDING_CHART) - (i * spacer)
                    )
                )

            }

            // Draw Eje Horizontal
            drawLine(
                start = Offset(x = PADDING_VERTICAL - 2, y = size.height - PADDING_CHART),
                end = Offset(x = size.width, y = size.height - PADDING_CHART),
                color = Color.Red,
                strokeWidth = 5f
            )

            // val unitHeight = (size.height / maxDataValue)
            val distanceInX = (size.width - PADDING_VERTICAL_AND_HORIZONTAL) / (list.size)


            // Paint bars on the graph
            list.forEachIndexed { index, bar ->
                val endPointInX = distanceInX * (index + 1) + PADDING_VERTICAL
                val barHeight = unitHeight * bar.value * animationProgress.value - (PADDING_CHART*2)
                val barWidth = distanceInX / 2
                val startY = size.height - barHeight - (PADDING_CHART*2)
                val startX = endPointInX - barWidth

                if (bar.value > 0) {
                    drawRect(
                        color = bar.color ?: Color.Gray,
                        topLeft = Offset(startX, startY),
                        size = Size(barWidth, barHeight),
                        //style = Stroke(width = 1.dp.toPx()),
                    )
                }
                val textLabel = context.resources.getText(bar.label).toString()
                val newValue = textMeasurer.measure(textLabel.substring(0, 3), newStyleLabel)
                drawText(
                    newValue,
                    topLeft = Offset(
                        x = startX,
                        y = size.height - 4
                    )
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewBarChart() {
   MaterialTheme {
       BarChart(list =
           listOf(Bar(
               R.string.copy_friday,
               855f,
           ))
       )
   }
}

private fun Int.converterText(): String {
    return when {
        this == 0 -> {
            "$0"
        }

        this >= 1000000 -> {
            "$${(this / 1000000)}m"
        }

        this >= 1000 -> {
            "$${(this / 1000)}k"
        }

        else -> "$this"
    }
}

data class Bar(
    @StringRes val label: Int,
    val value: Float,
    val color: Color? = null
)