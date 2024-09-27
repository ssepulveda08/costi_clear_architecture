package com.ssepulveda.presentation_home.home.ui.homeContainer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ssepulveda.presentation_common.inputs.DetailInput
import com.ssepulveda.presentation_common.navigation.NavRoutes
import com.ssepulveda.presentation_common.state.Loading
import com.ssepulveda.presentation_common.state.UiState
import com.ssepulveda.presentation_home.R
import com.ssepulveda.presentation_home.home.ui.GraphicsSection
import com.ssepulveda.presentation_home.home.ui.HeaderSection
import com.ssepulveda.presentation_home.home.ui.ItemBill
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeContainer(
    viewModel: HomeContainerViewModel,
    navController: NavController,
) {

    val uiState = viewModel.uiStateFlow.collectAsState()// remember { mutableStateOf() }

   /* LaunchedEffect(Unit) {
        viewModel.submitAction(HomeContainerUiAction.Load)
    } */
    /* viewModel.uiStateFlow.collectAsState().value.let { state ->
        /* CommonScreen(state = state) {
             Home(
                 viewModel,
                 it,
                 navController,
                 snackBarHostState
             )
         } */
     } */

    LaunchedEffect(Unit) {
        viewModel.singleEventFlow.collectLatest {
            when (it) {
                is HomeSingleEvent.OpenAddBill -> {
                    navController.navigate(it.navRoute)
                }
            }
        }
    }

    /*var uiEvent by remember {
        mutableStateOf<UIEvent?>(null)
    }

    CommonUIEvent(uiEvent, snackBarHostState) {
        viewModel.hideEventFlow()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collectLatest {
            if (uiEvent != it) {
                uiEvent = it
            }
        }
    }*/

    // TODO ADD NEW HOME

    when (uiState.value) {
        is UiState.Loading -> Loading()
        is UiState.Success -> {
            val model = (uiState.value as UiState.Success<HomeModel>).data
            Container(viewModel, model, navController)
        }
        else -> {

        }
    }

}

@Composable
private fun Container(
    viewModel: HomeContainerViewModel,
    homeModel: HomeModel?,
    navController: NavController,
) {
    LazyColumn(
        //contentPadding = innerPadding,
    ) {
        item {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background),
            ) {

                Spacer(modifier = Modifier.padding(4.dp))
                HeaderSection(homeModel)
                Spacer(modifier = Modifier.padding(4.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.inversePrimary, thickness = 0.5.dp
                )
                Spacer(modifier = Modifier.padding(8.dp))

                GraphicsSection(homeModel)

                Spacer(modifier = Modifier.padding(8.dp))

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = MaterialTheme.colorScheme.inversePrimary,
                    thickness = 0.5.dp
                )
                Text(
                    text = stringResource(id = R.string.copy_list_bills),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(vertical = 4.dp)
                        .fillMaxHeight(),
                    style = MaterialTheme.typography.titleSmall,
                )

                Spacer(modifier = Modifier.padding(4.dp))
            }

        }
        items(homeModel?.bills ?: listOf()) {
            ItemBill(
                it,
                homeModel?.localCode ?: "COP",
                onClick = { billId ->
                    navController.navigate(
                        NavRoutes.DetailBill.routeForDetail(
                            DetailInput(
                                billId.toLong()
                            )
                        )
                    )
                },
                onDelete = { model ->
                    viewModel.submitAction(HomeContainerUiAction.DeleteBill(model))
                }
            )
        }
        item {
            Spacer(modifier = Modifier.padding(30.dp))
        }
    }
}
