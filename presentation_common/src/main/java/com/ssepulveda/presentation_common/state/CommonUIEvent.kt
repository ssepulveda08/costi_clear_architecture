package com.ssepulveda.presentation_common.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.ssepulveda.presentation_common.ui.DialogController
import kotlinx.coroutines.launch

@Composable
fun CommonUIEvent(uiEvent: UIEvent?, snackBarHostState: SnackbarHostState, dismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    if (uiEvent is UIEvent.ShowSnackBar) {
        //CustomSnackBar(message = uiEvent.message, dismiss)
        scope.launch {
            snackBarHostState.showSnackbar(message = uiEvent.message)
        }
    }

    if (uiEvent is UIEvent.ShowDialog) {
        DialogController(uiEvent.dialog)
    }
}

/*@Composable
private fun CustomSnackBar(message: String, dismiss: () -> Unit) {
    // TODO UPDATE CHANGE
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Snackbar(

        ) {
            Text(text = message)
        }
    }
}*/