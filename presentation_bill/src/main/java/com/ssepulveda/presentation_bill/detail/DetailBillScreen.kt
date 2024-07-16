package com.ssepulveda.presentation_bill.detail

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavController
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
                FormBillComponent(
                    input = form,
                    navController = navController,
                    isEditable = true,
                    onCompleted = {
                        Log.d("POTATO", "On completed $it")
                    }
                )
            }

        }
    }

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {

        }
    }
}

