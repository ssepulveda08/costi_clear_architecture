package com.ssepulveda.presentation_common.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssepulveda.presentation_common.ui.AlertDialogExample


@Composable
fun <T : Any> CommonScreen(state: UiState<T>, onSuccess: @Composable (T?) -> Unit) {
    when (state) {
        is UiState.Loading -> {
            Loading()
        }

        is UiState.Error -> {
            Error(state.errorMessage)
        }

        is UiState.Success -> {
            onSuccess(state.data)
        }

        is UiState.ShowDialog -> {
            AlertDialogExample(
                "Dialog",
                state.message,
                Icons.Filled.AccountBox
            )
        }

        else -> onSuccess(null)
    }
}

@Composable
fun Error(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar {
            Text(text = errorMessage)
        }
    }
}

@Composable
fun Loading() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator()
    }
}