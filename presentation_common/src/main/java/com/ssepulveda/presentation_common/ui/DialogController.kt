package com.ssepulveda.presentation_common.ui

import androidx.compose.runtime.Composable
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.modal_dialogs.entities.DialogFactory


@Composable
fun <T> DialogController(
    dialog: Dialog?,
    onClickCancel: () -> Unit = {},
    onClickSuccess: (T) -> Unit = {}
) {
   if (dialog != null) {
       DialogFactory<T>(dialog)
   }
}