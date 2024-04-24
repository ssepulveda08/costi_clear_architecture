package com.ssepulveda.presentation_add_bill.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssepulveda.presentation_add_bill.ui.SingleToolbar
import com.ssepulveda.presentation_add_bill.ui.SpinnerComponent
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddBillScreen(
    viewModel: AddBillViewModel,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(AddBillUiAction.LoadData)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            FormAddBill(viewModel, navController)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is AddBillSingleEvent.AddBill -> {

                }
                is AddBillSingleEvent.Close -> {
                    navController.navigateUp()
                }
            }
        }
    }
}


@Composable
private fun FormAddBill(viewModel: AddBillViewModel, navController: NavController) {

    val types by viewModel.types.collectAsState()
    val subTypes by viewModel.filterSubType.collectAsState()
    val subTypeSelected by viewModel.subTypeSelected.collectAsState()
    val value by viewModel.value.collectAsState()
    val description by viewModel.description.collectAsState()
    val enabledButton by viewModel.enableButton.collectAsState()
    var selectedType by remember { mutableStateOf<ItemDropdown?>(null) }

    Scaffold(
        modifier = Modifier,
        topBar = {
            SingleToolbar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = {
                    viewModel.setDescription(it)
                },
                label = { Text(text = "Descripcion") },
            )
            Spacer(modifier = Modifier.padding(8.dp))
            SpinnerComponent(
                types,
                selectedType
            ) {
                selectedType = it
                viewModel.submitAction(AddBillUiAction.SelectedType(it))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            SpinnerComponent(subTypes, subTypeSelected) {
                viewModel.submitAction(AddBillUiAction.SelectedSubType(it))
            }
            Spacer(modifier = Modifier.padding(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { newValue ->
                    newValue.replace(",", ".")
                    viewModel.setValue(newValue.trim())
                },
                visualTransformation = MoneyVisualTransformation,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "Ingresa un valor") },
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Button(
                onClick = {
                    viewModel.submitAction(AddBillUiAction.AddBill)
                },
                enabled = enabledButton
            ) {
                Text(text = "Registrar", modifier = Modifier.padding(start = 32.dp, end = 32.dp))
            }
        }
    }
}

object MoneyVisualTransformation : VisualTransformation {

    override fun filter(text: AnnotatedString): TransformedText {
        val modifiedText = if (text.isNotEmpty()) "$ $text" else ""
        val a = AnnotatedString(text = modifiedText)
        return TransformedText(a, MyOffsetMapping())
    }

}

class MyOffsetMapping : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        if (offset <= 0) return 0
        return offset + 2
    }

    override fun transformedToOriginal(offset: Int): Int {
        if (offset <= 0) return 0
        return offset - 2
    }
}