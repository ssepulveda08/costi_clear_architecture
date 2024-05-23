package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.HomeModel

@Composable
fun HeaderSection(homeModel: HomeModel?) {
    val nameMonth = homeModel?.nameMonth ?: stringResource(R.string.copy_no_data)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
    ) {
        val annotatedString = buildAnnotatedString {
            append(nameMonth)
            withStyle(
                style = SpanStyle(
                    MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp
                )
            ) {
                append(" / ${homeModel?.currentDate.orEmpty()}")
            }
        }

        Text(
            annotatedString,
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .fillMaxHeight(),
            style = MaterialTheme.typography.headlineLarge,
        )


        Card(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
            ),
        ) {
            Text(
                text = "Detalle de Gastos",
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(top = 4.dp)
                    .fillMaxHeight(),
                style = MaterialTheme.typography.labelLarge,
            )
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                Text(
                    "Total: $${homeModel?.totalMonth?.toInt() ?: 0}",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    style = MaterialTheme.typography.titleLarge,
                )

                Text(
                    "Máximo: $${homeModel?.maxValue?.toInt() ?: 0}",
                    modifier = Modifier.padding(horizontal = 6.dp).padding(top = 4.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    "Mínimo: $${homeModel?.minValue?.toInt() ?: 0}",
                    modifier = Modifier.padding(horizontal = 6.dp),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HeaderSectionPreview() {
    MaterialTheme {
        Surface {
            HeaderSection(
                HomeModel(
                    1,
                    "Mayo",
                    585222.0,
                    8.0,
                    9.0,
                    "16 - may - 2024",
                    true,
                    listOf()
                )
            )
        }
    }
}
