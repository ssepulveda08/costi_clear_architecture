package com.ssepulveda.modal_dialogs.entities

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DialogFactory(dialog: Dialog) {
    return when (dialog) {
        is Dialog.DialogDefault -> {
            DialogTitleAndSubTitle(dialog)
        }
    }
}

@Composable
fun DialogTitleAndSubTitle(dialog: Dialog.DialogDefault) {
    AlertDialog(
        icon = {
           // Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialog.title)
        },
        text = {
            Text(text = dialog.description)
        },
        onDismissRequest = {
            dialog.onCancel.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dialog.onSuccess.invoke()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialog.onCancel.invoke()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}