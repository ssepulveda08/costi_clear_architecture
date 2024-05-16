package com.ssepulveda.presentation_common.ui

import androidx.compose.runtime.Composable
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.modal_dialogs.entities.DialogFactory

@Composable
fun DialogController(
    dialog: Dialog?
) {
   if (dialog != null) {
       DialogFactory(dialog)
   }
}