package com.ssepulveda.presentation_bill.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ssepulveda.presentation_bill.R
import com.ssepulveda.presentation_bill.ui.FormBillComponent
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddBillScreen(
    viewModel: AddBillViewModel,
    onBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(AddBillUiAction.LoadData)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            it?.let { input ->
                FormAddBill(input, viewModel, onBack)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is AddBillSingleEvent.AddBill -> {

                }

                is AddBillSingleEvent.Close -> onBack()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FormAddBill(
    input: FormInput,
    viewModel: AddBillViewModel,
    onBack: () -> Unit
) {

    FormBillComponent(
        input,
        false,
        stringResource(R.string.title_add_bill),
        {
            Text(
                text = stringResource(R.string.copy_information_add_bill),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
        {
            viewModel.submitAction(AddBillUiAction.AddBill(it))
        }, onBack = onBack
    )

    /*val types by viewModel.types.collectAsState()
    val subTypes by viewModel.filterSubType.collectAsState()
    val subTypeSelected by viewModel.subTypeSelected.collectAsState()
    val value by viewModel.value.collectAsState()
    val description by viewModel.description.collectAsState()
    val enabledButton by viewModel.enableButton.collectAsState()
    var selectedType by remember { mutableStateOf<ItemDropdown?>(null) }*/

    /*Scaffold(
        modifier = Modifier,
        topBar = {
            SingleToolbar(
                stringResource(R.string.title_add_bill),
                navController
            )
        }
    ) { innerPadding ->
       /* Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = stringResource(R.string.copy_information_add_bill),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = {
                    viewModel.setDescription(it)
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
                types,
                label = stringResource(R.string.label_type_bill),
                selectedType
            ) {
                selectedType = it
                viewModel.submitAction(AddBillUiAction.SelectedType(it))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            SpinnerComponent(
                items = subTypes,
                label = stringResource(R.string.label_subtype_bill),
                subTypeSelected
            ) {
                viewModel.submitAction(AddBillUiAction.SelectedSubType(it))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { newValue ->
                    newValue.replace(",", ".")
                    if (newValue.toDoubleOrNull() != null) {
                        viewModel.setValue(newValue.trim())
                    } else {
                        viewModel.setValue("")
                    }
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
            Button(
                onClick = {
                    viewModel.submitAction(AddBillUiAction.AddBill)
                },
                enabled = enabledButton
            ) {
                Text(
                    text = stringResource(R.string.copy_register),
                    modifier = Modifier.padding(start = 32.dp, end = 32.dp),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }*/

        FormBillComponent(

        )
    }*/
}
