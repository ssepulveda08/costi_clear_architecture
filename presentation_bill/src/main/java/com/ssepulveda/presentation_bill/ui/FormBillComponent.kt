package com.ssepulveda.presentation_bill.ui

import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssepulveda.presentation_bill.R
import com.ssepulveda.presentation_bill.add.ItemDropdown
import com.ssepulveda.presentation_common.ui.SingleToolbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormBillComponent(
    input: FormInput,
    navController: NavController,
    isEditable: Boolean,
    title: String,
    information: @Composable ColumnScope.() -> Unit = {},
    onCompleted: (FormInput) -> Unit
) {

    var mutableInput by remember {
        mutableStateOf(input)
    }

    var subTypes by remember {
        mutableStateOf<List<ItemDropdown>>(listOf())
    }

    val dateString: MutableState<String> = remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier,
        topBar = {
            SingleToolbar(
                title, //stringResource(R.string.title_edit_bill),
                navController
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.padding(8.dp))
            this.information()
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = mutableInput.description,
                onValueChange = {
                    mutableInput = mutableInput.copy(
                        description = it
                    )
                },
                label = {
                    Text(
                        text = stringResource(R.string.label_description),
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
            Spacer(modifier = Modifier.padding(8.dp))
            SpinnerComponent(
                mutableInput.types,
                label = stringResource(R.string.label_type_bill),
                mutableInput.typeSelect
            ) { item ->
                subTypes = mutableInput.subTypes.filter { it.subType == item.id }
                mutableInput = mutableInput.copy(
                    typeSelect = item,
                    subTypeSelect = null
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            SpinnerComponent(
                items = subTypes, //subTypesLocal,
                label = stringResource(R.string.label_subtype_bill),
                mutableInput.subTypeSelect
            ) {
                mutableInput = mutableInput.copy(subTypeSelect = it)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            // Description
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = mutableInput.value,
                onValueChange = { newValue ->
                    newValue.replace(",", ".")
                    val new = if (newValue.toDoubleOrNull() != null) {
                        newValue.trim()
                    } else {
                        ""
                    }
                    mutableInput = mutableInput.copy(
                        value = new
                    )
                },
                visualTransformation = MoneyVisualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = {
                    Text(
                        text = stringResource(R.string.label_value),
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
            Spacer(modifier = Modifier.padding(8.dp))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && isEditable) {
                CustomDataPicker(
                    input.recordDate ?: "",
                    dateString
                )
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    val newValue = if (isEditable) {
                        mutableInput.copy(recordDate = dateString.value)
                    } else {
                        mutableInput
                    }
                    onCompleted.invoke(newValue)
                },
                enabled = mutableInput.isCompleted()
            ) {
                val textButton = if (isEditable) {
                    stringResource(R.string.save_change)
                } else {
                    stringResource(R.string.copy_register)
                }
                Text(
                    text = textButton,
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
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
