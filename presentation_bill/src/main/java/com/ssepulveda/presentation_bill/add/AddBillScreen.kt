package com.ssepulveda.presentation_bill.add

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.ssepulveda.presentation_bill.R
import com.ssepulveda.presentation_bill.ui.FormBillComponent
import com.ssepulveda.presentation_bill.ui.FormInput
import com.ssepulveda.presentation_common.inputs.AddBillInput
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddBillScreen(
    viewModel: AddBillViewModel,
    navController: NavController,
    fromEntry: AddBillInput
) {
    viewModel.accountId = fromEntry.id
    LaunchedEffect(Unit) {
        viewModel.submitAction(AddBillUiAction.LoadData)
    }
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            it?.let { input ->
                FormAddBill(input, viewModel, navController)
            }
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
private fun FormAddBill(
    input: FormInput,
    viewModel: AddBillViewModel,
    navController: NavController
) {

    FormBillComponent(
        input,
        navController,
        false,
        stringResource(R.string.title_add_bill),
        {
            Text(
                text = stringResource(R.string.copy_information_add_bill),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        },
    ) {
        viewModel.submitAction(AddBillUiAction.AddBill(it))
    }
}
