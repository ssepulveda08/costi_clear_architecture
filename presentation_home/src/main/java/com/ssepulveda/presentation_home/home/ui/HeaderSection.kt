package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.HomeModel

@Composable
fun HeaderSection(homeModel: HomeModel?) {
    val nameMonth = homeModel?.nameMonth ?: stringResource(R.string.copy_no_data)
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background),
    ) {
        Text(
            nameMonth,
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.headlineLarge,
        )
        Text(
            "Total Mes: $ ${homeModel?.totalMonth?.toInt() ?: 0}",
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            homeModel?.currentDate.orEmpty(),
            modifier = Modifier.padding(horizontal = 4.dp),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
@Preview
private fun HeaderSectionPreview() {
    HeaderSection(
        HomeModel(
            1,
            "Mayo",
            585222.0,
            "16 - may - 2024",
            true,
            listOf()
        )
    )
}