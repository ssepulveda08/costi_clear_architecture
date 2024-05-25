package com.ssepulveda.presentation_menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension

@Composable
fun FooterMenu(constraintLayoutScope: ConstraintLayoutScope, footer: ConstrainedLayoutReference) {
    with(constraintLayoutScope) {
        Column(
            modifier = Modifier.constrainAs(footer) {
                top.linkTo(parent.baseline, margin = 8.dp)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp).padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.inversePrimary, thickness = 0.3.dp
            )
            Text(
                text = "@Github/ssepulveda08",
                style = MaterialTheme.typography.labelLarge,
            )
            Text(
                text = "2024 - V.0.1",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewFooter() {
    MaterialTheme {
        ConstraintLayout {
            val ref = createRef()
            FooterMenu(constraintLayoutScope = this, footer = ref)
        }
    }
}