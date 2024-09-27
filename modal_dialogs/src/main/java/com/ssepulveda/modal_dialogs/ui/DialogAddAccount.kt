package com.ssepulveda.modal_dialogs.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ssepulveda.modal_dialogs.R
import com.ssepulveda.modal_dialogs.entities.ActionDialog
import com.ssepulveda.modal_dialogs.entities.AddAccountForm
import com.ssepulveda.modal_dialogs.entities.Dialog

@Composable
fun DialogAddAccount(dialog: Dialog.DialogRegisterAccount) {
    val formData = remember {
        mutableStateOf(AddAccountForm())
    }
    AlertDialog(
        icon = {
            // Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(
                text = "Agregar Cuenta",
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            FormDialog(formData)
        },
        onDismissRequest = {
            dialog.onCancel.invoke()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val action = ActionDialog.AddAccount(formData.value)
                    dialog.onSuccess.invoke(action)
                },
                enabled = formData.value.name.isNotBlank()
            ) {
                Text(
                    "Agregar",
                    style = MaterialTheme.typography.labelMedium
                )
            }

        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialog.onCancel.invoke()
                }
            ) {
                Text(
                    "Cancelar",
                    style = MaterialTheme.typography.labelMedium
                )
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormDialog(formData: MutableState<AddAccountForm>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Agregar una nueva cuenta",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = formData.value.name,
            onValueChange = {
                formData.value = formData.value.copy(
                    name = it
                )
            },
            label = {
                Text(
                    text = "Nombre",
                    style = MaterialTheme.typography.labelMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
        Spacer(modifier = Modifier.padding(4.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = formData.value.capped.toString(),
            onValueChange = { newValue ->
                newValue.replace(",", ".").trim()
                formData.value = formData.value.copy(
                    capped = newValue.toDoubleOrNull() ?: 0.0
                )
            },
            visualTransformation = MoneyVisualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = {
                Text(
                    text = stringResource(R.string.label_tope),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
        )
    }
}

private object MoneyVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val modifiedText = if (text.isNotEmpty()) "$ $text" else ""
        val a = AnnotatedString(text = modifiedText)
        return TransformedText(a, MyOffsetMapping())
    }

}

private class MyOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 0) return 0
        return offset + 2
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 0) return 0
        return offset - 2
    }
}
