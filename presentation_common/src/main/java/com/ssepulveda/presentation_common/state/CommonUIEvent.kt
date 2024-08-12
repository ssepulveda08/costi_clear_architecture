package com.ssepulveda.presentation_common.state

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.ssepulveda.presentation_common.ui.DialogController
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CommonUIEvent(uiEvent: UIEvent?, snackBarHostState: SnackbarHostState, dismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    if (uiEvent is UIEvent.ShowSnackBar && snackBarHostState.currentSnackbarData == null) {
        Log.d("POTATO", "CommonUIEvent ShowSnackBar $snackBarHostState")
        scope.launch {
            snackBarHostState.showSnackbar(
                message = uiEvent.message,
                actionLabel = "OK",
                duration = SnackbarDuration.Short
            ).apply {
               dismiss.invoke()
            }
        }
    }

    if (uiEvent is UIEvent.ShowDialog) {
        DialogController(uiEvent.dialog)
    }
}
