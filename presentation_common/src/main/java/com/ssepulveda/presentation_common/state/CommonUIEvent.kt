package com.ssepulveda.presentation_common.state

import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ssepulveda.presentation_common.ui.DialogController

@Composable
fun CommonUIEvent(uiEvent: UIEvent?, dismiss: () -> Unit) {

   /* if (uiEvent is UIEvent.ShowSnackBar) {
        CustomSnackBar(message = uiEvent.message, dismiss)
    } */

    if (uiEvent is UIEvent.ShowDialog) {
        DialogController(uiEvent.dialog)
    }
}

@Composable
private fun CustomSnackBar(message: String, dismiss: () -> Unit) {
    Snackbar(
        dismissAction = {
            //dismiss.invoke()
        }
    ) {
        Text(text = message)
    }
}