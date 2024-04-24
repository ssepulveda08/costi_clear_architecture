package com.ssepulveda.presentation_home.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ssepulveda.costi.domain.entity.Result
import com.ssepulveda.costi.domain.useCase.GetAllBillsByMonthUseCase
import com.ssepulveda.costi.domain.useCase.GetCurrentMonthUseCase
import com.ssepulveda.costi.domain.useCase.GetHomeInformationUseCase
import com.ssepulveda.presentation_common.state.MviViewModel
import com.ssepulveda.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeInformationUseCase: GetHomeInformationUseCase,
    private val converter: HomerResultConverter,
) : MviViewModel<HomeModel, UiState<HomeModel>, HomeUiAction, HomeSingleEvent>() {

    private fun loadData() {
        viewModelScope.launch{
            getHomeInformationUseCase.execute(GetHomeInformationUseCase.Request).map {
                converter.convert(it)
            }.collect {
                submitState(it)
            }
        }
    }

    override fun initState(): UiState<HomeModel> = UiState.Loading

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.Load -> loadData()
        }
    }

}