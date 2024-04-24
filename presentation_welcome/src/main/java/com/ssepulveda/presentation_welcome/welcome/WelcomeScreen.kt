package com.ssepulveda.presentation_welcome.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel,
    navController: NavController
) {

    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            Information(viewModel)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is WelcomeSingleEvent.OpenHomeScreen -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
private fun Information(viewModel: WelcomeViewModel) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "¡Bienvenido!",
                fontSize = 32.sp
            )
            Text(
                "Con nuestra herramienta, podrás llevar un registro detallado de tus gastos y generar informes útiles para administrar mejor tus finanzas.",
                modifier = Modifier.padding(top = 8.dp),
                fontSize = 16.sp
            )
            Text(
                "¿Listo para empezar a tomar el control de tus finanzas?",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 16.sp
            )
            Text(
                "¡Comienza configurando tus datos ahora mismo!",
                modifier = Modifier.padding(top = 32.dp),
                fontSize = 16.sp
            )
            Button(
                onClick = {
                    viewModel.submitAction(WelcomeUiAction.LoadData)
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    "Comenzar"
                )
            }
        }
    }
}