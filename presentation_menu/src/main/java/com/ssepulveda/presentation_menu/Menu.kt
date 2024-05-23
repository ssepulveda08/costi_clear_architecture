package com.ssepulveda.presentation_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Menu() {
    Column(
      //  modifier = Modifier.width(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(32.dp))
        ImageProfile()
        Spacer(modifier = Modifier.padding(8.dp))
        HorizontalDivider(
            thickness = 0.5.dp,
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.inversePrimary
        )
        Spacer(modifier = Modifier.padding(8.dp))
        ItemMenu(
            "Item del menu"
        )
        ItemMenu(
            "Item del menu"
        )
        ItemMenu(
            "Item del menu"
        )
    }
}

@Composable
private fun ImageProfile() {
    Box(
        modifier = Modifier
            .size(90.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .background(
                    color = Color.Transparent,
                    shape = CircleShape
                )
                .border(
                    2.dp,
                    color = Color.Black, shape = CircleShape
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(90.dp),
                imageVector = Icons.Filled.Person,
                contentDescription = "Localized description",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun ItemMenu(
    name: String,
    // ADD RUTA
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = name
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 0.2.dp,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Preview(
    widthDp = 250
)
@Composable
private fun PreviewMenu() {
    Menu()
}