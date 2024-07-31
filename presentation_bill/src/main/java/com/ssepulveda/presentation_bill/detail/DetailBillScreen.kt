package com.ssepulveda.presentation_bill.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.ssepulveda.costi.domain.entity.Bill
import com.ssepulveda.presentation_bill.R
import com.ssepulveda.presentation_bill.ui.FormBillComponent
import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.state.CommonScreen
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailBillScreen(
    viewModel: DetailBillViewModel,
    input: DetailInput,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        viewModel.submitAction(DetailBillUiAction.LoadData(input))
    }

    viewModel.uiStateFlow.collectAsState().value.let { state ->
        CommonScreen(state = state) {
            it?.let { form ->
                //Log.d("POTATO", "Form ${form.typeSelect}")
                FormBillComponent(
                    input = form,
                    navController = navController,
                    title = stringResource(R.string.title_edit_bill),
                    isEditable = true,
                    onCompleted = {
                        viewModel.submitAction(DetailBillUiAction.UpdateBill(it))
                    }
                )
            }

        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when(it) {
                DetailBillSingleEvent.Close -> navController.popBackStack()
            }
        }
    }
}

