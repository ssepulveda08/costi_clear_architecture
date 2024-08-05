package com.ssepulveda.presentation_home.home.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_home.home.HomeModel
import com.ssepulveda.presentation_home.home.WeeklyReport
import com.ssepulveda.presentation_home.home.ui.charts.BarChart
import com.ssepulveda.presentation_home.home.ui.charts.PieChart
import com.ssepulveda.presentation_home.home.utils.toChangeFormatter

@Composable
fun GraphicsSection(homeModel: HomeModel?) {
    var option by remember {
        mutableIntStateOf(0)
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp
    ) {

        Column {
            TwoOptionToggle {
                option = it
            }
            if (option == 0) {
                //BarWeek(homeModel)
                CarouselBarWeek(homeModel)
            } else {
                PieChart(
                    homeModel?.reportForType ?: listOf(),
                    title = {
                        Text(
                            text = "Grafico por tipo",
                            modifier = Modifier.padding(
                                bottom = 2.dp,
                                start = 16.dp,
                                end = 8.dp
                            ),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(250.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BarWeek(report: WeeklyReport?) {
    // Todo change text title
    val startDate = report?.startDateWeed?.toChangeFormatter() ?: ""
    val endDate = report?.endDateWeed?.toChangeFormatter() ?: ""
    BarChart(
        report?.days ?: listOf(),
        //modifier = Modifier.padding(250.dp),
        title = {
            Column {

                Text(
                    text = "Grafico por semana",
                    modifier = Modifier.padding(
                        bottom = 2.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "$startDate / $endDate",
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    ),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
        modifier = Modifier
            .height(250.dp)
    )
}