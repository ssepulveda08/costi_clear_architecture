package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp


@Composable
fun TwoOptionToggle(
    onOptionSelected: (Int) -> Unit
) {
    var option by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = Modifier.padding(12.dp)
    ) {
        OutlinedButton(
            onClick = {
                option = 0
                onOptionSelected.invoke(0)
            },
            enabled = option != 0,
            shape = roundedLeftSquareRightShape(LocalDensity.current, 16.dp, 0.dp)
        ) {
            Text(text = "Por semana")
        }
        OutlinedButton(
            onClick = {
                option = 1
                onOptionSelected.invoke(1)
            },
            enabled = option != 1,
            shape = roundedLeftSquareRightShape(LocalDensity.current, 0.dp, 16.dp)
        ) {
            Text(text = "Por typo")
        }
    }
}

private fun roundedLeftSquareRightShape(
    currentDensity: Density,
    leftCornerRadius: Dp,
    rightCornerRadius: Dp
): Shape = object : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val leftRadiusPx = with(currentDensity) { leftCornerRadius.toPx() }
        val rightRadiusPx = with(currentDensity) { rightCornerRadius.toPx() }
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, size.width, size.height),
                    topLeft = CornerRadius(leftRadiusPx),
                    bottomLeft = CornerRadius(leftRadiusPx),
                    topRight = CornerRadius(rightRadiusPx),
                    bottomRight = CornerRadius(rightRadiusPx),
                )
            )
        }
        return Outline.Generic(path)
    }
}