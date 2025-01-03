package com.ssepulveda.presentation_menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ssepulveda.presentation_common.navigation.NavRoutes

@Composable
fun Menu(
    onNavigate: (route: String) -> Unit
) {
    ConstraintLayout {
        val (body, footer) = createRefs()
        Column(
            modifier = Modifier.constrainAs(body) {
                top.linkTo(parent.top, margin = 8.dp)
                bottom.linkTo(footer.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.matchParent
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(32.dp))
            ImageProfile()
            Spacer(modifier = Modifier.padding(8.dp))
            HorizontalDivider(
                thickness = 0.5.dp,
                modifier = Modifier.padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.inversePrimary
            )
            Spacer(modifier = Modifier.padding(8.dp))
            ItemMenu(
                stringResource(R.string.copy_report_for_month)
            ) {
                //navController?.navigate(NavRoutes.Months.route)
                onNavigate(NavRoutes.Months.route)
            }
            ItemMenu(
                stringResource(R.string.copy_github)
            ) {
                onNavigate(
                    NavRoutes.WebView.routeForUrl(
                        url = "GITHUB"
                    )
                )
            }
            ItemMenu(
                stringResource(R.string.copy_buy_me_coffee)
            ) {
                onNavigate(
                    NavRoutes.WebView.routeForUrl(
                        url = "LINKEDIN"
                    )
                )
            }
        }
        FooterMenu(this@ConstraintLayout, footer)
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
                    color = MaterialTheme.colorScheme.secondary,
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
                tint = Color.White
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = CircleShape
                    )
                    .border(
                        1.dp,
                        color = Color.Black, shape = CircleShape
                    )
                    .padding(4.dp),
                imageVector = Icons.Filled.Settings,
                contentDescription = "Localized description",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ItemMenu(
    name: String,
    onclick: () -> Unit
) {
    Column(
        modifier = Modifier
            .height(40.dp)
            .padding(horizontal = 8.dp)
            .clickable {
                onclick.invoke()
            }
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 4.dp),
            text = name,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 0.3.dp,
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
private fun PreviewMenu() {
    MaterialTheme {
        Menu {

        }
    }
}

@Preview(
    showSystemUi = true,
)
@Composable
private fun PreviewItem() {
    MaterialTheme {
        ItemMenu("null") {}
    }
}