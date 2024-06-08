package com.ssepulveda.presentation_home.home.ui.charts

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun PieChart(
    data: List<CircleChart>,
    title: @Composable ColumnScope.() -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.then(modifier)
    ) {
        this.title()
        Box(
            modifier = modifier
                .fillMaxSize()
        ) {
            //var values
            val animationProgress = remember { Animatable(0f) }
            LaunchedEffect(data) {
                animationProgress.animateTo(1f, animationSpec = tween(durationMillis = 2000))
            }
            val density = LocalDensity.current
            val textColor = MaterialTheme.colorScheme.onSurface.toArgb()
            Canvas(
                modifier = Modifier.fillMaxSize().padding(8.dp)
            ) {
                val listColor = listOf<Color>()
                val total = data.map { it.value }.sum()
                var startAngle = -90f

                data.forEachIndexed { index, model ->
                    val sweepAngle = (model.value / total) * 360 * animationProgress.value

                    //Draw Value circle
                    drawArc(
                        color = model.color ?: Color.Gray,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(
                            center.x - size.minDimension / 2,
                            center.y - size.minDimension / 2
                        ),
                        size = Size(size.minDimension, size.minDimension),
                        //  style = Stroke(width = 50f) // Change the width as needed
                    )

                    //Draw text
                    // Calcula la posición del texto
                    val angle = (startAngle + sweepAngle / 2) * PI / 180
                    val radius = size.minDimension / 3
                    val x = center.x + (cos(angle) * radius).toFloat()
                    val y = center.y + (sin(angle) * radius).toFloat()

                    // Validate % of te char
                    if (sweepAngle > 25) {
                        drawIntoCanvas { canvas ->
                            val scaledFontSize = with(density) { 16.sp.toPx() }
                            val paint = Paint().asFrameworkPaint().apply {
                                color = Color.Black.toArgb()
                                textAlign = android.graphics.Paint.Align.CENTER
                                textSize = scaledFontSize
                                //typeface = android.graphics.Typeface.create(font, android.graphics.Typeface.NORMAL)
                            }
                            //val newValue = textLabelPie.measure(model.label, newStyleLabel)

                            canvas.nativeCanvas.drawText(model.label, x, y, paint)

                        }

                    }
                    startAngle += sweepAngle
                }
            }
        }
    }

}


data class CircleChart(
    val value: Float,
    val label: String,
    val color: Color?,
)
