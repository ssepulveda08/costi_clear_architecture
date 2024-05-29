package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_common.ui.toCurrencyFormat
import com.ssepulveda.presentation_home.home.BillModel
import kotlin.random.Random

@Composable
fun ItemBill(
    billModel: BillModel,
    localeCode: String,
    onDelete: (BillModel) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 12.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RandomCircularIcon()
            Spacer(modifier = Modifier.padding(8.dp))
            Column {
                Text(
                    text = billModel.description.capitalize(Locale.current),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = billModel.value.toCurrencyFormat(localeCode),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.padding(2.dp))
                Text(
                    text = billModel.date,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = null,
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        onDelete.invoke(billModel)
                    },
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun RandomCircularIcon(modifier: Modifier = Modifier) {
    val iconsList = listOf(
        Icons.Filled.DateRange,
        Icons.Filled.AccountCircle,
        Icons.Filled.Face,
        Icons.Filled.Favorite,
        Icons.Filled.ShoppingCart,
        Icons.Filled.MailOutline,
    )

    val randomIndex = Random.nextInt(iconsList.size)
    val randomIcon = iconsList[randomIndex]
    Box(
        modifier = modifier
            .size(45.dp)
            .background(Color.Transparent)
            .padding(1.dp)
            .border(2.dp, MaterialTheme.colorScheme.primary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = randomIcon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun PreviewItemBill() {
    MaterialTheme {
        Row {
            ItemBill(
                billModel = BillModel(
                    8,
                    855.0,
                    "Name del item",
                    1,
                    "nameType",
                    ""
                ),
                "COP"
            ) {

            }
        }
    }
}
