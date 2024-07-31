package com.ssepulveda.presentation_bill.ui

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.tooling.preview.Preview
import com.ssepulveda.presentation_bill.R
import com.ssepulveda.presentation_bill.utils.DateUtils
import com.ssepulveda.presentation_bill.utils.DateUtils.convertMillisToDate
import com.ssepulveda.presentation_bill.utils.DateUtils.convertMillisToLocalDate
import com.ssepulveda.presentation_bill.utils.DateUtils.convertStringDateToMillis
import com.ssepulveda.presentation_bill.utils.DateUtils.dateToString
import com.ssepulveda.presentation_bill.utils.DateUtils.toFormatterString
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDataPicker(
    dateString: String,
    dateToString: MutableState<String>
) {
    val dateState =
        rememberDatePickerState(initialSelectedDateMillis = convertStringDateToMillis(dateString))

    val colorText = MaterialTheme.colorScheme.onSurface

    var showDialog by remember { mutableStateOf(false) }
    dateToString.value = dateState.selectedDateMillis?.let { millis ->
        convertMillisToDate(millis)
    } ?: stringResource(id = R.string.label_enter_value)

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = dateToString.value,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    showDialog = true
                }),
            enabled = false,
            label = {
                Text(
                    text = stringResource(R.string.label_description),
                    style = MaterialTheme.typography.labelMedium
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Localized description"
                )
            },
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = MaterialTheme.shapes.small,

            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledTextColor = colorText,
                disabledLeadingIconColor = colorText
            ),
        )
        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                        }
                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = dateState,
                    showModeToggle = true
                )
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCustomDataPicker() {

}