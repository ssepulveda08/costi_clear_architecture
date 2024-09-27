package com.ssepulveda.modal_dialogs.entities

import androidx.compose.runtime.Composable
import com.ssepulveda.modal_dialogs.ui.DialogAddAccount
import com.ssepulveda.modal_dialogs.ui.DialogTitleAndSubTitle

@Composable
fun <T> DialogFactory(
    dialog: Dialog
) {
    return when (dialog) {
        is Dialog.DialogDefault -> {
            DialogTitleAndSubTitle(dialog)
        }

        is Dialog.DialogRegisterAccount -> DialogAddAccount(dialog)
    }
}