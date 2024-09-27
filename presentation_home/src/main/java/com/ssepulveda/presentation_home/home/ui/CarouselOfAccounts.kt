package com.ssepulveda.presentation_home.home.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssepulveda.presentation_home.home.ui.account.TabsAccount

private const val MAX_ACCOUNTS = 5

@Composable
fun CarouselOfAccounts(
    list: List<TabsAccount>,
    onClickTab: (TabsAccount) -> Unit,
    onAddTab: () -> Unit,
) {
    LazyRow {
        items(list) {
            CarouselItem(it, onClick = {
                onClickTab(it)
            })
        }
        if (list.size < MAX_ACCOUNTS) {
            item {
                CarouselItem(TabsAccount(9999, "Agregar Cuenta", false, 0.0), onClick = onAddTab) {
                    Icon(
                        Icons.Default.Add, "",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun CarouselItem(
    tab: TabsAccount,
    onClick: () -> Unit,
    icon: @Composable () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .border(
                width = 2.dp,
                color = if (tab.selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.inverseOnSurface
                },
                shape = RoundedCornerShape(16.dp)
            )
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = tab.name,
                style = MaterialTheme.typography.labelLarge,
            )
            icon()
        }

    }
}

@Preview
@Composable
private fun PreviewCarouselItem() {
    CarouselItem(TabsAccount(45, "Cuenta principal", true, 0.0), onClick = {

    })
}

@Preview
@Composable
private fun PreviewCarouselList() {
    CarouselOfAccounts(
        listOf(
            TabsAccount(
                1, "", false, 0.0
            )
        ),
        {}, {}
    )
}