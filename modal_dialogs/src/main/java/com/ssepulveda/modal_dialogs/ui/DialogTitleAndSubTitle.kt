package com.ssepulveda.modal_dialogs.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.ssepulveda.modal_dialogs.entities.ActionDialog
import com.ssepulveda.modal_dialogs.entities.Dialog
import com.ssepulveda.modal_dialogs.entities.OnClickCancel
import com.ssepulveda.modal_dialogs.entities.OnClickSuccess

@Composable
fun DialogTitleAndSubTitle(
    dialog: Dialog.DialogDefault
) {
    AlertDialog(
        icon = {
            // Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(
                text = dialog.title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = dialog.description,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        onDismissRequest = {
            dialog.onCancel.invoke()
        },
        confirmButton = {
            dialog.textSuccess?.let { success ->
                TextButton(
                    onClick = {
                        dialog.onSuccess.invoke()
                    }
                ) {
                    Text(
                        success,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        },
        dismissButton = {
            dialog.textCancel?.let { cancel ->
                TextButton(
                    onClick = {
                        dialog.onCancel.invoke()
                    }
                ) {
                    Text(
                        cancel,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    )
}
