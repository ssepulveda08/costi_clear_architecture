package com.ssepulveda.presentation_welcome.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssepulveda.presentation_common.state.CommonScreen
import com.ssepulveda.presentation_welcome.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navController: NavController?
) {

    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Information {
                viewModel.submitAction(WelcomeUiAction.LoadData)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is WelcomeSingleEvent.OpenHomeScreen -> {
                    navController?.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
private fun Information(onclick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.padding(32.dp))
            Text(
                stringResource(R.string.copy_welcome),
                fontSize = 32.sp,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                stringResource(R.string.welcome_information),
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                stringResource(R.string.welcome_ask),
                modifier = Modifier.padding(top = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                stringResource(R.string.welcome_go),
                modifier = Modifier.padding(top = 32.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            Button(
                onClick = {
                    onclick.invoke()
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(
                    stringResource(R.string.copy_start),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewWelcomeScreen() {
    MaterialTheme {
        Information {

        }
    }
}