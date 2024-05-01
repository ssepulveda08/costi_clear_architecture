package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_home.home.HomeModel
import com.ssepulveda.presentation_home.home.ui.charts.Bar
import com.ssepulveda.presentation_home.home.ui.charts.BarChart
import com.ssepulveda.presentation_home.home.ui.charts.PieChart

@Composable
fun GraphicsSection(homeModel: HomeModel?) {
    var option by remember {
        mutableIntStateOf(0)
    }
    Column(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .height(250.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        TwoOptionToggle {
            option = it
        }
        if (option == 0) {
            BarChart(
                homeModel?.reportForWeek ?: listOf(),
                title = {
                    Text(
                        text = "Grafico por semana",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                background = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .fillMaxWidth()
            )
        } else {
            PieChart(
                homeModel?.reportForType ?: listOf(),
                title = {
                    Text(
                        text = "Grafico por tipo",
                        modifier = Modifier.padding(8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                background = MaterialTheme.colorScheme.background,
            )
        }
    }
}